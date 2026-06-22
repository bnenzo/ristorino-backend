package ar.edu.ubp.das.ristorino_backend.beans.preferencias;

public class PreferenciaResponseBean {

  private String cod_categoria;
  private Integer nro_valor_dominio;
  private String nom_valor_dominio;

  public String getCod_categoria() {
    return cod_categoria;
  }

  public void setCod_categoria(String cod_categoria) {
    this.cod_categoria = cod_categoria;
  }

  public Integer getNro_valor_dominio() {
    return nro_valor_dominio;
  }

  public void setNro_valor_dominio(Integer nro_valor_dominio) {
    this.nro_valor_dominio = nro_valor_dominio;
  }

  public String getNom_valor_dominio() {
    return nom_valor_dominio;
  }

  public void setNom_valor_dominio(String nom_valor_dominio) {
    this.nom_valor_dominio = nom_valor_dominio;
  }
}