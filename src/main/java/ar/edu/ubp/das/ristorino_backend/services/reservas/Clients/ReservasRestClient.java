package ar.edu.ubp.das.ristorino_backend.services.reservas.Clients;

import ar.edu.ubp.das.ristorino_backend.components.Httpful;
import ar.edu.ubp.das.ristorino_backend.config.beans.ConfigBean;
import ar.edu.ubp.das.ristorino_backend.services.reservas.Dto.CrearReservaConClienteDTO;

public class ReservasRestClient {

  public void crearReserva(ConfigBean config, CrearReservaConClienteDTO payload) {

    System.out.println(
        "[ReservasRestClient] Enviando reserva + cliente a backend REST: "
            + config.getBaseUrl());

    Httpful http = new Httpful(config.getBaseUrl())
        .path("/reservas")
        .post(payload);

    http.execute(Void.class);

    System.out.println(
        "[ReservasRestClient] Reserva enviada correctamente");
  }
}
