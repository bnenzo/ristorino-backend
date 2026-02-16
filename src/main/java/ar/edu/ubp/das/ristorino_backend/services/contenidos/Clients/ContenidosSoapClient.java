package ar.edu.ubp.das.ristorino_backend.services.contenidos.Clients;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.edu.ubp.das.ristorino_backend.beans.ContenidoNoPublicadoBean;
import ar.edu.ubp.das.ristorino_backend.config.beans.ConfigBean;
import ar.edu.ubp.das.ristorino_backend.config.soapClient.SoapClientFactory;
import ar.edu.ubp.das.ristorino_backend.services.contenidos.Dto.ActualizarContenidosNoPublicadosDTO;
import ar.edu.ubp.das.ristorino_backend.utils.SOAPClient;

public class ContenidosSoapClient {

  @SuppressWarnings("unchecked")
  public List<ContenidoNoPublicadoBean> obtenerContenidosNoPublicados(ConfigBean config) {

    System.out.println(
        "[ContenidosSoapClient] Llamando backend SOAP: " + config.getBaseUrl());

    SOAPClient client = SoapClientFactory.create(
        config,
        "ObtenerContenidosNoPublicadosRequest");

    Map<String, Object> params = new HashMap<>();

    return client.callServiceForList(
        ContenidoNoPublicadoBean.class,
        "ObtenerContenidosNoPublicadosResponse",
        params);
  }

  // ACTUALIZAR LOS CONTENIDOS NO PUBLICADOS A PUBLICADOS
  public void actualizarContenidoNoPublicadosAPublicados(ConfigBean config,
      ActualizarContenidosNoPublicadosDTO body) {

    System.out.println(
        "[ContenidosSoapClient] Llamando backend SOAP: " + config.getBaseUrl());

    SOAPClient client = SoapClientFactory.create(
        config,
        "ActualizarContenidosNoPublicadosRequest");

    Map<String, Object> params = new HashMap<>();
    params.put("body", body);

    client.callServiceForObject(Void.class,
        "", params);
  }
}
