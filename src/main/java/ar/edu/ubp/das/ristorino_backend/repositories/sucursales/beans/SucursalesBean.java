package ar.edu.ubp.das.ristorino_backend.repositories.sucursales.beans;

public class SucursalesBean {

  private Integer nroRestaurante;
  private Integer nroSucursal;
  private String nomSucursal;
  private String calle;
  private Integer nroCalle;
  private String barrio;
  private Integer nroLocalidad;
  private String codPostal;
  private String telefonos;
  private Integer totalComensales;
  private Integer minToleranciaReserva;
  private String codSucursalRestaurante;

  public Integer getNroRestaurante() {
    return nroRestaurante;
  }

  public void setNroRestaurante(Integer nroRestaurante) {
    this.nroRestaurante = nroRestaurante;
  }

  public Integer getNroSucursal() {
    return nroSucursal;
  }

  public void setNroSucursal(Integer nroSucursal) {
    this.nroSucursal = nroSucursal;
  }

  public String getNomSucursal() {
    return nomSucursal;
  }

  public void setNomSucursal(String nomSucursal) {
    this.nomSucursal = nomSucursal;
  }

  public String getCalle() {
    return calle;
  }

  public void setCalle(String calle) {
    this.calle = calle;
  }

  public Integer getNroCalle() {
    return nroCalle;
  }

  public void setNroCalle(Integer nroCalle) {
    this.nroCalle = nroCalle;
  }

  public String getBarrio() {
    return barrio;
  }

  public void setBarrio(String barrio) {
    this.barrio = barrio;
  }

  public Integer getNroLocalidad() {
    return nroLocalidad;
  }

  public void setNroLocalidad(Integer nroLocalidad) {
    this.nroLocalidad = nroLocalidad;
  }

  public String getCodPostal() {
    return codPostal;
  }

  public void setCodPostal(String codPostal) {
    this.codPostal = codPostal;
  }

  public String getTelefonos() {
    return telefonos;
  }

  public void setTelefonos(String telefonos) {
    this.telefonos = telefonos;
  }

  public Integer getTotalComensales() {
    return totalComensales;
  }

  public void setTotalComensales(Integer totalComensales) {
    this.totalComensales = totalComensales;
  }

  public Integer getMinToleranciaReserva() {
    return minToleranciaReserva;
  }

  public void setMinToleranciaReserva(Integer minToleranciaReserva) {
    this.minToleranciaReserva = minToleranciaReserva;
  }

  public String getCodSucursalRestaurante() {
    return codSucursalRestaurante;
  }

  public void setCodSucursalRestaurante(String codSucursalRestaurante) {
    this.codSucursalRestaurante = codSucursalRestaurante;
  }
}
