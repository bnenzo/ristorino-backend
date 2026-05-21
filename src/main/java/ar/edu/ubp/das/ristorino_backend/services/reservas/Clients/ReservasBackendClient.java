package ar.edu.ubp.das.ristorino_backend.services.reservas.Clients;

import ar.edu.ubp.das.ristorino_backend.config.beans.ConfigBean;
import ar.edu.ubp.das.ristorino_backend.resources.reservas.beans.ActualizarReservaClienteRequestBean;
import ar.edu.ubp.das.ristorino_backend.services.reservas.Dto.CrearReservaConClienteDTO;

public interface ReservasBackendClient {

  void crearReserva(
      ConfigBean config,
      CrearReservaConClienteDTO payload);

  // posiblemente borrar
  void actualizarReservaCliente(
      ConfigBean config,
      ActualizarReservaClienteRequestBean request);
}