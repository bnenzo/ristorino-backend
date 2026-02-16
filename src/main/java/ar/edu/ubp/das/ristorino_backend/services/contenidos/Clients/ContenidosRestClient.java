package ar.edu.ubp.das.ristorino_backend.services.contenidos.Clients;

import java.util.Arrays;
import java.util.List;
import ar.edu.ubp.das.ristorino_backend.beans.ContenidoNoPublicadoBean;
import ar.edu.ubp.das.ristorino_backend.components.Httpful;
import ar.edu.ubp.das.ristorino_backend.config.beans.ConfigBean;
import ar.edu.ubp.das.ristorino_backend.services.contenidos.Dto.ActualizarContenidosNoPublicadosDTO;

public class ContenidosRestClient {

  public List<ContenidoNoPublicadoBean> obtenerContenidosNoPublicados(ConfigBean config) {

    System.out.println(
        "[ContenidosRestClient] Llamando backend REST: " + config.getBaseUrl());

    Httpful http = new Httpful(config.getBaseUrl())
        .path("/contenidos/no-publicados")
        .get();

    ContenidoNoPublicadoBean[] response = http.execute(ContenidoNoPublicadoBean[].class);

    List<ContenidoNoPublicadoBean> contenidos = Arrays.asList(response);

    System.out.println(
        "[ContenidosRestClient] Contenidos recibidos: " + contenidos.size());

    return contenidos;
  }

  // ACTUALIZAR LOS CONTENIDOS NO PUBLICADOS A PUBLICADOS
  public void actualizarContenidoNoPublicadosAPublicados(ConfigBean config,
      ActualizarContenidosNoPublicadosDTO body) {

    System.out.println(
        "[ContenidosRestClient] Llamando backend REST: " + config.getBaseUrl());

    Httpful http = new Httpful(config.getBaseUrl())
        .path("/contenidos/actualizar-publicados")
        .post(body);

    http.execute(Void.class);
  }
}
