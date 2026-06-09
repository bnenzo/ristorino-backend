package ar.edu.ubp.das.ristorino_backend.components.ApiHandler;

public class ApiOperationConfig {
  public final String restPath;
  public final String restMethod;
  public final String soapOperationName;
  public final String soapResponseWrapper;

  public ApiOperationConfig(String restPath, String restMethod, String soapOperationName, String soapResponseWrapper) {
    this.restPath = restPath;
    this.restMethod = restMethod;
    this.soapOperationName = soapOperationName;
    this.soapResponseWrapper = soapResponseWrapper;
  }
}