package ar.edu.ubp.das.ristorino_backend.services.contenidos.Clients;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.ubp.das.ristorino_backend.beans.ContenidoNoPublicadoBean;
import ar.edu.ubp.das.ristorino_backend.beans.SoapStringResponseBean;
import ar.edu.ubp.das.ristorino_backend.config.beans.ConfigBean;
import ar.edu.ubp.das.ristorino_backend.config.soapClient.SoapClientFactory;
import ar.edu.ubp.das.ristorino_backend.services.contenidos.Dto.ActualizarContenidosNoPublicadosDTO;
import ar.edu.ubp.das.ristorino_backend.utils.SOAPClient;

@Component("SOAP")
public class ContenidosSoapClient implements ContenidosBackendClient {

  public List<ContenidoNoPublicadoBean> obtenerContenidosNoPublicados(ConfigBean config) {

    System.out.println(
        "[ContenidosSoapClient] Llamando backend SOAP: " + config.getBaseUrl());

    SOAPClient client = SoapClientFactory.create(
        config,
        "ObtenerContenidosNoPublicadosRequest");

    Map<String, Object> params = new HashMap<>();

    List<SoapStringResponseBean> response = client.callServiceForList(
        SoapStringResponseBean.class,
        "ObtenerContenidosNoPublicadosResponse",
        params);

    String responseString = response.get(0).getResponse();

    try {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(responseString, new TypeReference<List<ContenidoNoPublicadoBean>>() {
      });
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Error al parsear contenidos no publicados", e);
    }
  }

  // ACTUALIZAR LOS CONTENIDOS NO PUBLICADOS A PUBLICADOS
  public void actualizarContenidoNoPublicadosAPublicados(ConfigBean config,
      ActualizarContenidosNoPublicadosDTO body) {

    System.out.println(
        "[ContenidosSoapClient] Llamando backend SOAP: " + config.getBaseUrl());

    SOAPClient client = SoapClientFactory.create(
        config,
        "ActualizarContenidosNoPublicadosRequest");

    try {
      ObjectMapper mapper = new ObjectMapper();
      String jsonBody = mapper.writeValueAsString(body);

      Map<String, Object> params = new HashMap<>();
      params.put("Body", jsonBody);

      client.callServiceForObject(Void.class, "", params);

    } catch (JsonProcessingException e) {
      throw new RuntimeException("Error al serializar body", e);
    }
  }
}
