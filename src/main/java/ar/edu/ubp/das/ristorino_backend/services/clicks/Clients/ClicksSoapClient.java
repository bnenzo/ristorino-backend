package ar.edu.ubp.das.ristorino_backend.services.clicks.Clients;

import java.util.HashMap;
import java.util.Map;

import ar.edu.ubp.das.ristorino_backend.beans.ClicksContenidosRestaurantesBean;
import ar.edu.ubp.das.ristorino_backend.config.beans.ConfigBean;
import ar.edu.ubp.das.ristorino_backend.config.soapClient.SoapClientFactory;
import ar.edu.ubp.das.ristorino_backend.services.clicks.utils.ClicksUtils;
import ar.edu.ubp.das.ristorino_backend.utils.SOAPClient;

public class ClicksSoapClient {
  public void registrarClickContenido(ConfigBean config, ClicksContenidosRestaurantesBean click) {

    SOAPClient client = SoapClientFactory.create(
        config,
        "RegistrarClickContenidoRequest");

    ClicksContenidosRestaurantesBean body = new ClicksContenidosRestaurantesBean(click);

    body.setNroRestaurante(
        ClicksUtils.obtenerNroRestaurante(body.getCodContenidoRestaurante()));
    body.setNroCliente(1);

    Map<String, Object> params = new HashMap<>();
    params.put("body", body);

    client.callServiceForObject(Void.class,
        "", params);
  }
}
