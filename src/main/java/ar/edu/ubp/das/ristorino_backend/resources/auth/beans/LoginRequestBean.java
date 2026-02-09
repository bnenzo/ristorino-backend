package ar.edu.ubp.das.ristorino_backend.resources.auth.beans;

public class LoginRequestBean {
  private String email;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  private String clave;

  public String getClave() {
    return clave;
  }

  public void setClave(String clave) {
    this.clave = clave;
  }
}
