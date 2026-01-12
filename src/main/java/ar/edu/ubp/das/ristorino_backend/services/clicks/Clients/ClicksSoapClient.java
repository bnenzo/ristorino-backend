package ar.edu.ubp.das.ristorino_backend.services.clicks.Clients;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import ar.edu.ubp.das.ristorino_backend.beans.ClicksContenidosRestaurantesBean;
import ar.edu.ubp.das.ristorino_backend.config.beans.ConfigBean;
import ar.edu.ubp.das.ristorino_backend.config.soapClient.SoapClientFactory;
import ar.edu.ubp.das.ristorino_backend.utils.SOAPClient;

public class ClicksSoapClient {
  public void registrarClickContenido(ConfigBean config, ClicksContenidosRestaurantesBean click) {

    SOAPClient client = SoapClientFactory.create(
        config,
        "RegistrarClickContenidoRequest");

    // ClicksContenidosRestaurantesBean clickContenidoRestaurante = new
    // ClicksContenidosRestaurantesBean();
    // clickContenidoRestaurante.setCostoClick(BigDecimal.valueOf(1));
    // clickContenidoRestaurante.setFechaHoraRegistro("2026-01-07T10:30:00");
    // clickContenidoRestaurante.setNroClick(1);
    // clickContenidoRestaurante.setNroCliente(1);
    // clickContenidoRestaurante.setNroContenido(1);
    // clickContenidoRestaurante.setNroIdioma(1);
    // clickContenidoRestaurante.setNroRestaurante(1);

    click.setNroCliente(1);

    Map<String, Object> params = new HashMap<>();
    params.put("body", click);

    client.callServiceForObject(Void.class,
        "", params);
  }
}
