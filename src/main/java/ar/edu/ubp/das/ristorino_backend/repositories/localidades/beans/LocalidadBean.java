package ar.edu.ubp.das.ristorino_backend.repositories.localidades.beans;

public class LocalidadBean {

  private Integer nro_localidad;
  private String nom_localidad;
  private String cod_provincia;

  public Integer getNro_localidad() {
    return nro_localidad;
  }

  public void setNro_localidad(Integer nro_localidad) {
    this.nro_localidad = nro_localidad;
  }

  public String getNom_localidad() {
    return nom_localidad;
  }

  public void setNom_localidad(String nom_localidad) {
    this.nom_localidad = nom_localidad;
  }

  public String getCod_provincia() {
    return cod_provincia;
  }

  public void setCod_provincia(String cod_provincia) {
    this.cod_provincia = cod_provincia;
  }
}