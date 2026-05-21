package ar.edu.ubp.das.ristorino_backend.config.beans;

public class ConfigBean {

  private String baseUrl;
  private String namespace;
  private String serviceName;
  private String portName;
  private String backendType;
  private String prefix;
  private String soapUsername;
  private String soapPassword;
  private String restSecretKey;

  public ConfigBean(
      String baseUrl,
      String namespace,
      String serviceName,
      String portName,
      String backendType,
      String prefix,
      String soapUsername,
      String soapPassword,
      String restSecretKey) {
    this.baseUrl = baseUrl;
    this.namespace = namespace;
    this.serviceName = serviceName;
    this.portName = portName;
    this.backendType = backendType;
    this.prefix = prefix;
    this.soapUsername = soapUsername;
    this.soapPassword = soapPassword;
    this.restSecretKey = restSecretKey;
  }

  public String getBaseUrl() {
    return baseUrl;
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

  public String getBackendType() {
    return backendType;
  }

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public String getSoapUsername() {
    return soapUsername;
  }

  public void setSoapUsername(String soapUsername) {
    this.soapUsername = soapUsername;
  }

  public String getSoapPassword() {
    return soapPassword;
  }

  public void setSoapPassword(String soapPassword) {
    this.soapPassword = soapPassword;
  }

  public String getRestSecretKey() {
    return restSecretKey;
  }

  public void setRestSecretKey(String restSecretKey) {
    this.restSecretKey = restSecretKey;
  }
}
