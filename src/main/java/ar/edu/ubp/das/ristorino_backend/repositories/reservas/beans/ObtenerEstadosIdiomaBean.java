package ar.edu.ubp.das.ristorino_backend.repositories.reservas.beans;

public class ObtenerEstadosIdiomaBean {
  private String codEstado;
  private String estado;

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public void setCodEstado(String codEstado) {
    this.codEstado = codEstado;
  }

  public String getCodEstado() {
    return codEstado;
  }
}
