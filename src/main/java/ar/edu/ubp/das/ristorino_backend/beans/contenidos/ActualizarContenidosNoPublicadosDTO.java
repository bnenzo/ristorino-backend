package ar.edu.ubp.das.ristorino_backend.beans.contenidos;

import java.util.List;

import ar.edu.ubp.das.ristorino_backend.beans.ContenidoNoPublicadoBean;
import jakarta.xml.bind.annotation.XmlRootElement;

public class ActualizarContenidosNoPublicadosDTO {
  List<ContenidoNoPublicadoBean> contenidos;

  public List<ContenidoNoPublicadoBean> getContenidos() {
    return contenidos;
  }

  public void setContenidos(List<ContenidoNoPublicadoBean> contenidos) {
    this.contenidos = contenidos;
  }
}
