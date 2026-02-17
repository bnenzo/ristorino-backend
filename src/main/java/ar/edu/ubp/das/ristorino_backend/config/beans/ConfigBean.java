package ar.edu.ubp.das.ristorino_backend.config.beans;

public class ConfigBean {

  private String baseUrl;
  private String namespace;
  private String serviceName;
  private String portName;
  private String backendType;
  private String prefix;

  public ConfigBean(
      String baseUrl,
      String namespace,
      String serviceName,
      String portName,
      String backendType,
      String prefix) {
    this.baseUrl = baseUrl;
    this.namespace = namespace;
    this.serviceName = serviceName;
    this.portName = portName;
    this.backendType = backendType;
    this.prefix = prefix;
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
}
