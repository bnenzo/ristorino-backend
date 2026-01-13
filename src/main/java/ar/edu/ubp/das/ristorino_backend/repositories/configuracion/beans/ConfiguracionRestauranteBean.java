package ar.edu.ubp.das.ristorino_backend.repositories.configuracion.beans;

public class ConfiguracionRestauranteBean {
  private Integer nroRestaurante;
  private String codAtributo;
  private String valor;

  public ConfiguracionRestauranteBean() {
  }

  public Integer getNroRestaurante() {
    return nroRestaurante;
  }

  public void setNroRestaurante(Integer nroRestaurante) {
    this.nroRestaurante = nroRestaurante;
  }

  public String getCodAtributo() {
    return codAtributo;
  }

  public void setCodAtributo(String codAtributo) {
    this.codAtributo = codAtributo;
  }

  public String getValor() {
    return valor;
  }

  public void setValor(String valor) {
    this.valor = valor;
  }
}
