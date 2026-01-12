package ar.edu.ubp.das.ristorino_backend.repositories.configuracion.beans;

public class ConfiguracionSOAPBean {
  private Integer nroRestaurante;
  private String codAtributo;
  private String valor;

  public ConfiguracionSOAPBean() {
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


}
