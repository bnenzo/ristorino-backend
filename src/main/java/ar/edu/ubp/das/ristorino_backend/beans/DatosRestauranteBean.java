package ar.edu.ubp.das.ristorino_backend.beans;

public class DatosRestauranteBean {
  private String nombreRestaurante;
  private String cuit;

  public String getNombreRestaurante() {
    return nombreRestaurante;
  }

  public void setNombreRestaurante(String nombreRestaurante) {
    this.nombreRestaurante = nombreRestaurante;
  }

  public String getCuit() {
    return cuit;
  }

  public void setCuit(String cuit) {
    this.cuit = cuit;
  }
}
