package ar.edu.ubp.das.ristorino_backend.beans.idiomas;

public class IdiomasResponseBean {
  private Integer nroIdioma;
  private String nomIdioma;
  private String codIdioma;

  public String getCodIdioma() {
    return codIdioma;
  }

  public void setCodIdioma(String codIdioma) {
    this.codIdioma = codIdioma;
  }

  public Integer getNroIdioma() {
    return nroIdioma;
  }

  public void setNroIdioma(Integer nroIdioma) {
    this.nroIdioma = nroIdioma;
  }

  public String getNomIdioma() {
    return nomIdioma;
  }

  public void setNomIdioma(String nomIdioma) {
    this.nomIdioma = nomIdioma;
  }
}
