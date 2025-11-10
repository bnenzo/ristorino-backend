package ar.edu.ubp.das.ristorino_backend.beans;

public class SucursalRestauranteBean {

  private Integer nroSucursal;
  private String nombreSucursal;
  private String direccionCompleta; // Concatenamos calle, nro_calle, barrio, cod_postal
  private String telefonos;
  private String localidad;
  private String provincia;

  public Integer getNroSucursal() {
    return nroSucursal;
  }

  public void setNroSucursal(Integer nroSucursal) {
    this.nroSucursal = nroSucursal;
  }

  public String getNombreSucursal() {
    return nombreSucursal;
  }

  public void setNombreSucursal(String nombreSucursal) {
    this.nombreSucursal = nombreSucursal;
  }

  public String getDireccionCompleta() {
    return direccionCompleta;
  }

  public void setDireccionCompleta(String direccionCompleta) {
    this.direccionCompleta = direccionCompleta;
  }

  public String getTelefonos() {
    return telefonos;
  }

  public void setTelefonos(String telefonos) {
    this.telefonos = telefonos;
  }

  public String getLocalidad() {
    return localidad;
  }

  public void setLocalidad(String localidad) {
    this.localidad = localidad;
  }

  public String getProvincia() {
    return provincia;
  }

  public void setProvincia(String provincia) {
    this.provincia = provincia;
  }
}
