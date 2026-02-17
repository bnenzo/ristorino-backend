package ar.edu.ubp.das.ristorino_backend.repositories.preferencias.beans;

public class ObtenerPreferenciasSucursalRestauranteBean {
  private String id;
  private String tags;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }
}
