package ar.edu.ubp.das.ristorino_backend.resources.auth.beans;

public class ObtenerInformacionUsuarioParaLogin {
  private String clave;
  private String correo;
  private Integer nroCliente;

  public void setClave(String clave) {
    this.clave = clave;
  }

  public void setCorreo(String correo) {
    this.correo = correo;
  }

  public void setNroCliente(Integer nroCliente) {
    this.nroCliente = nroCliente;
  }

  public String getClave() {
    return clave;
  }

  public String getCorreo() {
    return correo;
  }

  public Integer getNroCliente() {
    return nroCliente;
  }
}
