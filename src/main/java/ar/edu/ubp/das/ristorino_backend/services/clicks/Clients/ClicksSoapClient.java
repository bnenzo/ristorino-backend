package ar.edu.ubp.das.ristorino_backend.services.clicks.Clients;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.ubp.das.ristorino_backend.beans.ClicksContenidosRestaurantesBean;
import ar.edu.ubp.das.ristorino_backend.config.beans.ConfigBean;
import ar.edu.ubp.das.ristorino_backend.config.soapClient.SoapClientFactory;
import ar.edu.ubp.das.ristorino_backend.utils.SOAPClient;

@Component("SOAP-CLICKS")
public class ClicksSoapClient implements ClicksBackendClient {
  public void registrarClickContenido(ConfigBean config, ClicksContenidosRestaurantesBean click) {

    SOAPClient client = SoapClientFactory.create(
        config,
        "RegistrarClickContenidoRequest");

    ObjectMapper mapper = new ObjectMapper();

    String stringBody = mapper.createObjectNode()
        .put("nroRestaurante", 1)
        .put("nroContenido", click.getNroContenido())
        .put("nroClick", click.getNroClick())
        .put("fechaHoraRegistro", click.getFechaHoraRegistro())
        .put("nroCliente", 1)
        .put("costoClick", click.getCostoClick())
        .toString();

    Map<String, Object> params = new HashMap<>();
    params.put("Body", stringBody);
    client.callServiceForObject(Void.class,
        "", params);

  }
}
