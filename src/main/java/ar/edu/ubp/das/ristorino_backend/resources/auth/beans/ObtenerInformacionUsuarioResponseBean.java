package ar.edu.ubp.das.ristorino_backend.resources.auth.beans;

public class ObtenerInformacionUsuarioResponseBean {
  private String nombre;
  private String apellido;
  private String correo;
  private Integer nroCliente;

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public void setCorreo(String correo) {
    this.correo = correo;
  }

  public void setNroCliente(Integer nroCliente) {
    this.nroCliente = nroCliente;
  }

  public String getNombre() {
    return nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public String getCorreo() {
    return correo;
  }

  public Integer getNroCliente() {
    return nroCliente;
  }
}
