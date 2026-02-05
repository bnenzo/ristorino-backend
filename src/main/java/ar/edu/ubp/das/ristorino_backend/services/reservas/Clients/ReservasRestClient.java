package ar.edu.ubp.das.ristorino_backend.services.reservas.Clients;

import com.google.gson.JsonObject;

import ar.edu.ubp.das.ristorino_backend.components.Httpful;
import ar.edu.ubp.das.ristorino_backend.config.beans.ConfigBean;
import ar.edu.ubp.das.ristorino_backend.resources.reservas.beans.ActualizarReservaClienteRequestBean;

public class ReservasRestClient {
  public void actualizarReservaCliente(ConfigBean config, ActualizarReservaClienteRequestBean request) {

    JsonObject body = new JsonObject();
    body.addProperty("fechaReserva", request.getFechaReserva());
    body.addProperty("cantAdultos", request.getCantAdultos());
    body.addProperty("horaReserva", request.getHoraReserva());
    body.addProperty("codReservaSucursal", request.getCodReservaSucursal());
    body.addProperty("fechaCancelacion", request.getFechaCancelacion());

    Httpful http = new Httpful(config.getBaseUrl())
        .path("/reservas/cliente")
        .put(body);

    http.execute(String.class);
  }
}
