package ar.edu.ubp.das.ristorino_backend.infrastructure.clicks;

import java.math.BigDecimal;

import com.google.gson.JsonObject;

import ar.edu.ubp.das.ristorino_backend.beans.ClicksContenidosRestaurantesBean;
import ar.edu.ubp.das.ristorino_backend.components.Httpful;

public class ClicksRestClient {

  public void registrarClickContenido() {

    JsonObject body = new JsonObject();
    body.addProperty("nroRestaurante", 1);
    body.addProperty("nroContenido", 1);
    body.addProperty("nroClick", 1);
    body.addProperty("fechaHoraRegistro", "2026-01-07T10:30:00");
    body.addProperty("nroCliente", 1);
    body.addProperty("costoClick", BigDecimal.valueOf(1));

    Httpful http = new Httpful("http://localhost:8086/api/v1/la-bella-pizza")
        .path("/registrar_click_contenido")
        .post(body);

    http.execute(String.class);
  }
}
