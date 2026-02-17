package ar.edu.ubp.das.ristorino_backend.services.contenidos.Dto;

public class BuscadorPromocionesIAInputDTO {

  private String id;
  private String descripcion;

  public BuscadorPromocionesIAInputDTO(String id, String descripcion) {
    this.id = id;
    this.descripcion = descripcion;
  }

  public String getId() {
    return id;
  }

  public String getDescripcion() {
    return descripcion;
  }
}
