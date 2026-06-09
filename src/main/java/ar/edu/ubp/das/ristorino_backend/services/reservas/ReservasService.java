package ar.edu.ubp.das.ristorino_backend.services.reservas;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.ubp.das.ristorino_backend.beans.ClienteBean;
import ar.edu.ubp.das.ristorino_backend.components.ApiHandler.ApiHandler;
import ar.edu.ubp.das.ristorino_backend.config.beans.ConfigBean;
import ar.edu.ubp.das.ristorino_backend.repositories.clientes.ClienteRepository;
import ar.edu.ubp.das.ristorino_backend.repositories.configuracion.ConfiguracionRepository;
import ar.edu.ubp.das.ristorino_backend.repositories.costos.CostosRepository;
import ar.edu.ubp.das.ristorino_backend.repositories.reservas.ReservasRepository;
import ar.edu.ubp.das.ristorino_backend.repositories.reservas.beans.ObtenerDisponibilidadTurnosResponseBean;
import ar.edu.ubp.das.ristorino_backend.resources.reservas.beans.CrearReservaRequestBean;
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

  @Transactional
  public String crearReserva(CrearReservaRequestBean request, Integer nroCliente) {

    // STEP-2: consumir del restaurante
    List<ObtenerDisponibilidadTurnosResponseBean> horariosDisponibles = reservasRepository
        .obtenerDisponibilidadDeTurnosV2(
            request.getNroRestaurante(), request.getNroSucursal(),
            request.getFechaReserva(), request.getCodZona());

    String horaRequest = request.getHoraReserva().toString(); // "12:00"

    Optional<ObtenerDisponibilidadTurnosResponseBean> opt = horariosDisponibles.stream()
        .filter(h -> request.getNroRestaurante().equals(h.getNroRestaurante()) &&
            request.getNroSucursal().equals(h.getNroSucursal()) &&
            horaRequest.equals(h.getHoraDesde().substring(0, 5)))
        .findFirst();

    if (opt.isEmpty()) {
      // no existe turno para esa hora
      throw new RuntimeException("No hay turno para la hora solicitada");
    }

    ObtenerDisponibilidadTurnosResponseBean turno = opt.get();

    // Validaciones sobre el encontrado
    if (turno.getHabilitado() != null && turno.getHabilitado() == 0) {
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

    new ApiHandler(config, "CrearReserva").execute(payload);

    return codReservaSucursal;
  }
}
