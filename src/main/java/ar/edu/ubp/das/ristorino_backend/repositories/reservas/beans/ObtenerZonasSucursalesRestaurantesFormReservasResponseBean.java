package ar.edu.ubp.das.ristorino_backend.repositories.reservas.beans;

public class ObtenerZonasSucursalesRestaurantesFormReservasResponseBean {

  private Integer nroRestaurante;
  private Integer nroSucursal;
  private String codZona;
  private String zona;
  private Integer permiteMenores;
  private Integer habilitada;

  public Integer getHabilitada() {
    return habilitada;
  }

  public void setHabilitada(Integer habilitada) {
    this.habilitada = habilitada;
  }

  public Integer getPermiteMenores() {
    return permiteMenores;
  }

  public void setPermiteMenores(Integer permiteMenores) {
    this.permiteMenores = permiteMenores;
  }

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

  public String getCodZona() {
    return codZona;
  }

  public void setCodZona(String codZona) {
    this.codZona = codZona;
  }

  public String getZona() {
    return zona;
  }

  public void setZona(String zona) {
    this.zona = zona;
  }
}
