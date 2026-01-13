package ar.edu.ubp.das.ristorino_backend.config.soapClient;

public class SoapClientConfig {

  private String wsdlUrl;
  private String namespace;
  private String serviceName;
  private String portName;
  private String username;
  private String password;

  public SoapClientConfig(
      String wsdlUrl,
      String namespace,
      String serviceName,
      String portName,
      String username,
      String password) {
    this.wsdlUrl = wsdlUrl;
    this.namespace = namespace;
    this.serviceName = serviceName;
    this.portName = portName;
    this.username = username;
    this.password = password;
  }

  public String getWsdlUrl() {
    return wsdlUrl;
  }

  public String getNamespace() {
    return namespace;
  }

  public String getServiceName() {
    return serviceName;
  }

  public String getPortName() {
    return portName;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }
}