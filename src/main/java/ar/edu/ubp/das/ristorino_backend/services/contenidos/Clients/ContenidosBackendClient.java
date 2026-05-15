package ar.edu.ubp.das.ristorino_backend.services.contenidos.Clients;

import java.util.List;

import ar.edu.ubp.das.ristorino_backend.beans.ContenidoNoPublicadoBean;
import ar.edu.ubp.das.ristorino_backend.config.beans.ConfigBean;
import ar.edu.ubp.das.ristorino_backend.services.contenidos.Dto.ActualizarContenidosNoPublicadosDTO;

public interface ContenidosBackendClient {
  void actualizarContenidoNoPublicadosAPublicados(ConfigBean config,
      ActualizarContenidosNoPublicadosDTO clickContenido);

  List<ContenidoNoPublicadoBean> obtenerContenidosNoPublicados(ConfigBean config);

}
