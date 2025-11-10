package ar.edu.ubp.das.ristorino_backend.beans;

public class PreferenciaRestauranteBean {
  private Integer nroRestaurante;
  private String codCategoria;
  private String nomCategoria;
  private Integer nroValorDominio;
  private String nomValorDominio;
  private Integer nroPreferencia;
  private String observaciones;
  private Integer nroSucursal;

  public Integer getNroRestaurante() {
    return nroRestaurante;
  }

  public void setNroRestaurante(Integer nroRestaurante) {
    this.nroRestaurante = nroRestaurante;
  }

  public String getCodCategoria() {
    return codCategoria;
  }

  public void setCodCategoria(String codCategoria) {
    this.codCategoria = codCategoria;
  }

  public String getNomCategoria() {
    return nomCategoria;
  }

  public void setNomCategoria(String nomCategoria) {
    this.nomCategoria = nomCategoria;
  }

  public Integer getNroValorDominio() {
    return nroValorDominio;
  }

  public void setNroValorDominio(Integer nroValorDominio) {
    this.nroValorDominio = nroValorDominio;
  }

  public String getNomValorDominio() {
    return nomValorDominio;
  }

  public void setNomValorDominio(String nomValorDominio) {
    this.nomValorDominio = nomValorDominio;
  }

  public Integer getNroPreferencia() {
    return nroPreferencia;
  }

  public void setNroPreferencia(Integer nroPreferencia) {
    this.nroPreferencia = nroPreferencia;
  }

  public String getObservaciones() {
    return observaciones;
  }

  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }

  public Integer getNroSucursal() {
    return nroSucursal;
  }

  public void setNroSucursal(Integer nroSucursal) {
    this.nroSucursal = nroSucursal;
  }
}
