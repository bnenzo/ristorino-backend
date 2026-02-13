package ar.edu.ubp.das.ristorino_backend.repositories.sucursales.beans;

public class ZonasSucursalesBean extends SucursalesBean {
  private String codZona;

  public String getCodZona() {
    return codZona;
  }

  public void setCodZona(String codZona) {
    this.codZona = codZona;
  }

  private Integer permiteMenores;

  public Integer getPermiteMenores() {
    return permiteMenores;
  }

  public void setPermiteMenores(Integer permiteMenores) {
    this.permiteMenores = permiteMenores;
  }

  private Integer habilitada;

  public Integer getHabilitada() {
    return habilitada;
  }

  public void setHabilitada(Integer habilitada) {
    this.habilitada = habilitada;
  }
}
