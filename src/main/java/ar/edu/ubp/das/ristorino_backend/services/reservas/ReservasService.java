package ar.edu.ubp.das.ristorino_backend.services.reservas;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.ubp.das.ristorino_backend.config.beans.ConfigBean;
import ar.edu.ubp.das.ristorino_backend.repositories.configuracion.ConfiguracionRepository;
import ar.edu.ubp.das.ristorino_backend.repositories.reservas.ReservasRepository;
import ar.edu.ubp.das.ristorino_backend.resources.reservas.beans.ActualizarReservaClienteRequestBean;
import ar.edu.ubp.das.ristorino_backend.resources.reservas.beans.CrearReservaRequestBean;
import ar.edu.ubp.das.ristorino_backend.services.clicks.Clients.ClicksRestClient;
import ar.edu.ubp.das.ristorino_backend.services.clicks.Clients.ClicksSoapClient;
import ar.edu.ubp.das.ristorino_backend.services.reservas.Clients.ReservasRestClient;
import ar.edu.ubp.das.ristorino_backend.services.reservas.Clients.ReservasSoapClient;

@Service
public class ReservasService {

  private final ReservasRestClient rest;
  private final ReservasSoapClient soap;

  public ReservasService() {
    this.rest = new ReservasRestClient();
    this.soap = new ReservasSoapClient();
  }

  @Autowired
  private ReservasRepository reservasRepository;
  @Autowired
  private ConfiguracionRepository configuracionRepository;

  // REVISAR!
  @Transactional
  public void crearReserva(CrearReservaRequestBean request) {

    // Hora desde (viene del front)
    LocalTime horaDesde = request.getHoraDesde();

    // Hora hasta = hora desde + 1 hora
    LocalTime horaHasta = horaDesde.plusHours(1);

    // Insert en DB vía repository
    reservasRepository.insertarTurnoSucursal(
        request.getNroRestaurante(),
        request.getNroSucursal(),
        horaDesde,
        horaHasta);
  }

  public Void actualizarReservaCliente(Integer nroCliente, Integer nroReserva,
      ActualizarReservaClienteRequestBean request) {

    reservasRepository.actualizarReservaCliente(nroCliente, nroReserva, request);
    ConfigBean config = configuracionRepository.obtenerConfiguracionRestaunte(request.getNroRestaurante());
    if ("SOAP".equals(config.getBackendType())) {
      soap.actualizarReservaCliente(config, request);
      return null;
    }
    rest.actualizarReservaCliente(config, request);
    return null;
  }
}
