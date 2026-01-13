package ar.edu.ubp.das.ristorino_backend.beans;

import java.math.BigDecimal;

public class RegistrarClickPromocionBody {

  private Integer nroRestaurante;
  private Integer nroIdioma;
  private Integer nroContenido;
  private Integer nroCliente;
  private BigDecimal costoClick;
  private String codContenidoRestaurante;

  public RegistrarClickPromocionBody() {
  }

  public Integer getNroRestaurante() {
    return nroRestaurante;
  }

  public void setNroRestaurante(Integer nroRestaurante) {
    this.nroRestaurante = nroRestaurante;
  }

  public Integer getNroIdioma() {
    return nroIdioma;
  }

  public void setNroIdioma(Integer nroIdioma) {
    this.nroIdioma = nroIdioma;
  }

  public Integer getNroContenido() {
    return nroContenido;
  }

  public void setNroContenido(Integer nroContenido) {
    this.nroContenido = nroContenido;
  }

  public Integer getNroCliente() {
    return nroCliente;
  }

  public void setNroCliente(Integer nroCliente) {
    this.nroCliente = nroCliente;
  }

  public BigDecimal getCostoClick() {
    return costoClick;
  }

  public void setCostoClick(BigDecimal costoClick) {
    this.costoClick = costoClick;
  }

  public String getCodContenidoRestaurante() {
    return codContenidoRestaurante;
  }

  public void setCodContenidoRestaurante(String codContenidoRestaurante) {
    this.codContenidoRestaurante = codContenidoRestaurante;
  }

}
