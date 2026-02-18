package ar.edu.ubp.das.ristorino_backend.services.reservas.Clients;

import com.google.gson.JsonObject;

import ar.edu.ubp.das.ristorino_backend.components.Httpful;
import ar.edu.ubp.das.ristorino_backend.config.beans.ConfigBean;
import ar.edu.ubp.das.ristorino_backend.resources.reservas.beans.ActualizarReservaClienteRequestBean;
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

  public void actualizarReservaCliente(ConfigBean config, ActualizarReservaClienteRequestBean request) {

    JsonObject body = new JsonObject();
    body.addProperty("fechaReserva", request.getFechaReserva());
    body.addProperty("cantAdultos", request.getCantAdultos());
    body.addProperty("cantMenores", request.getCantMenores());
    body.addProperty("horaReserva", request.getHoraReserva());
    body.addProperty("codReservaSucursal", request.getCodReservaSucursal());
    body.addProperty("fechaCancelacion", request.getFechaCancelacion());
    body.addProperty("codEstado", request.getCodEstado());

    Httpful http = new Httpful(config.getBaseUrl())
        .path("/reservas/cliente")
        .put(body);

    http.execute(String.class);
  }
}
