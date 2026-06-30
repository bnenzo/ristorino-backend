package ar.edu.ubp.das.ristorino_backend.beans.clicks;

import java.math.BigDecimal;

public class RegistrarClickPromocionRequest {

  private Integer nroRestaurante;
  private Integer nroContenido;
  private String codContenidoRestaurante;

  public RegistrarClickPromocionRequest() {
  }

  public Integer getNroRestaurante() {
    return nroRestaurante;
  }

  public void setNroRestaurante(Integer nroRestaurante) {
    this.nroRestaurante = nroRestaurante;
  }

  public Integer getNroContenido() {
    return nroContenido;
  }

  public void setNroContenido(Integer nroContenido) {
    this.nroContenido = nroContenido;
  }

  public String getCodContenidoRestaurante() {
    return codContenidoRestaurante;
  }

  public void setCodContenidoRestaurante(String codContenidoRestaurante) {
    this.codContenidoRestaurante = codContenidoRestaurante;
  }

}
