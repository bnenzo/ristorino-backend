package ar.edu.ubp.das.ristorino_backend.services.reservas;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.ubp.das.ristorino_backend.beans.ClienteBean;
import ar.edu.ubp.das.ristorino_backend.config.beans.ConfigBean;
import ar.edu.ubp.das.ristorino_backend.repositories.clientes.ClienteRepository;
import ar.edu.ubp.das.ristorino_backend.repositories.configuracion.ConfiguracionRepository;
import ar.edu.ubp.das.ristorino_backend.repositories.costos.CostosRepository;
import ar.edu.ubp.das.ristorino_backend.repositories.reservas.ReservasRepository;
import ar.edu.ubp.das.ristorino_backend.repositories.reservas.beans.ObtenerDisponibilidadTurnosBean;
import ar.edu.ubp.das.ristorino_backend.resources.reservas.beans.ActualizarReservaClienteRequestBean;
import ar.edu.ubp.das.ristorino_backend.resources.reservas.beans.CrearReservaRequestBean;
import ar.edu.ubp.das.ristorino_backend.services.reservas.Clients.ReservasRestClient;
import ar.edu.ubp.das.ristorino_backend.services.reservas.Clients.ReservasSoapClient;
import ar.edu.ubp.das.ristorino_backend.services.reservas.Dto.ClienteRestauranteDTO;
import ar.edu.ubp.das.ristorino_backend.services.reservas.Dto.CrearReservaConClienteDTO;
import ar.edu.ubp.das.ristorino_backend.services.reservas.Dto.CrearReservaRestauranteDTO;
import ar.edu.ubp.das.ristorino_backend.utils.GeneradorCodigoReserva;

@Service
public class ReservasService {
  @Autowired
  private ConfiguracionRepository configuracionRepository;
  @Autowired
  private ReservasRepository reservasRepository;
  @Autowired
  private ClienteRepository clientesRepository;
  @Autowired
  private CostosRepository costosRepository;

  private final ReservasRestClient restClient;
  private final ReservasSoapClient soapClient;

  public ReservasService() {
    this.restClient = new ReservasRestClient();
    this.soapClient = new ReservasSoapClient();
  }

  public ReservasService(ReservasRestClient restClient, ReservasSoapClient soapClient) {
    this.restClient = restClient;
    this.soapClient = soapClient;
  }

  @Transactional
  public String crearReserva(CrearReservaRequestBean request, Integer nroCliente) {

    List<ObtenerDisponibilidadTurnosBean> horariosDisponibles = reservasRepository.obtenerDisponibilidadDeTurnos(
        request.getNroRestaurante(), request.getNroSucursal(),
        request.getFechaReserva());

    Optional<ObtenerDisponibilidadTurnosBean> opt = horariosDisponibles.stream()
        .filter(h -> request.getNroRestaurante().equals(h.getNroRestaurante()) &&
            request.getNroSucursal().equals(h.getNroSucursal()) &&
            request.getFechaReserva().equals(h.getFechaReserva()) &&
            request.getHoraReserva().equals(h.getHoraReserva()))
        .findFirst();

    if (opt.isEmpty()) {
      // no existe turno para esa hora
      throw new RuntimeException("No hay turno para la hora solicitada");
    }

    ObtenerDisponibilidadTurnosBean turno = opt.get();

    // Validaciones sobre el encontrado
    if (turno.getTurnoCerrado() != null && turno.getTurnoCerrado() == 1) {
      throw new RuntimeException("El turno está cerrado");
    }
    if (turno.getTurnoHabilitado() != null && turno.getTurnoHabilitado() == 0) {
      throw new RuntimeException("El turno no está habilitado");
    }
    if (turno.getCupoDisponible() == null || turno.getCupoDisponible() <= 0) {
      throw new RuntimeException("No hay cupo disponible");
    }
    if (turno.getCupoDisponible() < request.getCantAdultos() + request.getCantMenores()) {
      throw new RuntimeException("No hay cupo disponible para la cantidad de comensales solicitada");
    }

    // Generamos el codigo de reserva
    String codReservaSucursal = GeneradorCodigoReserva.generarCodigoReserva();

    // Obtener costo dinámico
    BigDecimal costoReserva = costosRepository
        .obtenerCostoPorTipo("RESERVA")
        .getMonto();

    // Insertamos en ristorino
    reservasRepository.crearReservaRestaurante(
        nroCliente,
        request.getFechaReserva(),
        request.getNroRestaurante(),
        request.getNroSucursal(),
        request.getCodZona(),
        request.getHoraReserva(),
        request.getCantAdultos(),
        request.getCantMenores(),
        "PEN",
        costoReserva.doubleValue(),
        codReservaSucursal);

    // Obtenemos todo el cliente
    ClienteBean cliente = clientesRepository.obtenerClientePorId(
        nroCliente);
    System.out.println("CLIENTE OBTENIDO: " + cliente.getNombre());

    // Obtenemos la configuracion
    ConfigBean config = configuracionRepository
        .obtenerConfiguracionRestaunte(request.getNroRestaurante());

    // Armamos DTO de RESERVA
    CrearReservaRestauranteDTO reservaDTO = new CrearReservaRestauranteDTO();
    reservaDTO.setCodReserva(codReservaSucursal);
    reservaDTO.setNroCliente(nroCliente);
    reservaDTO.setFechaReserva(request.getFechaReserva().toString());
    reservaDTO.setNroRestaurante(1);
    reservaDTO.setNroSucursal(request.getNroSucursal());
    reservaDTO.setCodZona(request.getCodZona());
    reservaDTO.setHoraReserva(request.getHoraReserva().toString());
    reservaDTO.setCantAdultos(request.getCantAdultos());
    reservaDTO.setCantMenores(request.getCantMenores());
    reservaDTO.setCostoReserva(costoReserva.doubleValue());

    // Armamos DTO de CLIENTE
    ClienteRestauranteDTO clienteDTO = new ClienteRestauranteDTO();
    clienteDTO.setNroCliente(cliente.getNroCliente());
    clienteDTO.setApellido(cliente.getApellido());
    clienteDTO.setNombre(cliente.getNombre());
    clienteDTO.setCorreo(cliente.getCorreo());
    clienteDTO.setTelefonos(cliente.getTelefonos());

    // Wrapper (cliente + reserva)
    CrearReservaConClienteDTO payload = new CrearReservaConClienteDTO();
    payload.setCliente(clienteDTO);
    payload.setReserva(reservaDTO);

    // Envío al restaurante correspondiente
    if ("SOAP".equalsIgnoreCase(config.getBackendType())) {
      System.out.println(">>> Enviando reserva + cliente a RESTAURANTE SOAP");
      soapClient.crearReserva(config, payload);
    } else {
      System.out.println(">>> Enviando reserva + cliente a RESTAURANTE REST");
      restClient.crearReserva(config, payload);
    }

    return codReservaSucursal;
  }

  public Void actualizarReservaCliente(Integer nroCliente, Integer nroReserva,
      ActualizarReservaClienteRequestBean request) {

    reservasRepository.actualizarReservaCliente(nroCliente, nroReserva, request);
    ConfigBean config = configuracionRepository.obtenerConfiguracionRestaunte(request.getNroRestaurante());
    if ("SOAP".equals(config.getBackendType())) {
      soapClient.actualizarReservaCliente(config, request);
      return null;
    }
    restClient.actualizarReservaCliente(config, request);
    return null;
  }
}
