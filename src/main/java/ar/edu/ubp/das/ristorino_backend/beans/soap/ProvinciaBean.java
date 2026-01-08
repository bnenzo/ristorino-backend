package ar.edu.ubp.das.ristorino_backend.beans.soap;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ProvinciasResponse")
public class ProvinciaBean {
  private String codProvincia;

  public String getCodProvincia() {
    return codProvincia;
  }

  public void setCodProvincia(String codProvincia) {
    this.codProvincia = codProvincia;
  }

  private String nomProvincia;

  public String getNomProvincia() {
    return nomProvincia;
  }

  public void setNomProvincia(String nomProvincia) {
    this.nomProvincia = nomProvincia;
  }
}
