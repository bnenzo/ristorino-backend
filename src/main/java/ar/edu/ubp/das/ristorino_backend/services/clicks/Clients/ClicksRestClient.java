package ar.edu.ubp.das.ristorino_backend.services.clicks.Clients;

import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;

import ar.edu.ubp.das.ristorino_backend.beans.ClicksContenidosRestaurantesBean;
import ar.edu.ubp.das.ristorino_backend.components.Httpful;
import ar.edu.ubp.das.ristorino_backend.config.beans.ConfigBean;

@Component("REST-CLICKS")
public class ClicksRestClient implements ClicksBackendClient {
  public void registrarClickContenido(ConfigBean config, ClicksContenidosRestaurantesBean click) {

    JsonObject body = new JsonObject();
    body.addProperty("nroRestaurante", 1);
    body.addProperty("nroContenido", click.getNroContenido());
    body.addProperty("nroClick", click.getNroClick());
    body.addProperty("fechaHoraRegistro", click.getFechaHoraRegistro());
    body.addProperty("nroCliente", 1);
    body.addProperty("costoClick", click.getCostoClick());

    Httpful http = new Httpful(config.getBaseUrl())
        .path("/registrar_click_contenido")
        .post(body);

    http.execute(String.class);
  }
}
