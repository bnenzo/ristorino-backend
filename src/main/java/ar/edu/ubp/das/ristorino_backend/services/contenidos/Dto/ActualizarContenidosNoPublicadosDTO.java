package ar.edu.ubp.das.ristorino_backend.services.contenidos.Dto;

import java.util.List;

import ar.edu.ubp.das.ristorino_backend.beans.ContenidoNoPublicadoBean;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ActualizarContenidosNoPublicadosRequest")
public class ActualizarContenidosNoPublicadosDTO {
  List<ContenidoNoPublicadoBean> contenidos;

  public List<ContenidoNoPublicadoBean> getContenidos() {
    return contenidos;
  }

  public void setContenidos(List<ContenidoNoPublicadoBean> contenidos) {
    this.contenidos = contenidos;
  }
}
