package ar.edu.ubp.das.ristorino_backend.resources.auth.beans;

public class LoginResponseBean {
  private String token;

  public LoginResponseBean(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }
}
