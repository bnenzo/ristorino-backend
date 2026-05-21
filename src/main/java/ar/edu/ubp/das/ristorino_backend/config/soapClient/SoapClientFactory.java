package ar.edu.ubp.das.ristorino_backend.config.soapClient;

import ar.edu.ubp.das.ristorino_backend.config.beans.ConfigBean;
import ar.edu.ubp.das.ristorino_backend.utils.SOAPClient;

public class SoapClientFactory {

  public static SOAPClient create(
      ConfigBean config,
      String operationName) {

    String jsonConfig = String.format("""
        {
          "wsdlUrl": "%s",
          "namespace": "%s",
          "serviceName": "%s",
          "portName": "%s",
          "username": "%s",
          "password": "%s"
        }
        """,
        config.getBaseUrl(),
        config.getNamespace(),
        config.getServiceName(),
        config.getPortName(),
        config.getSoapUsername(),
        config.getSoapPassword());

    return SOAPClient.SOAPClientBuilder
        .fromConfig(jsonConfig)
        .operationName(operationName)
        .build();
  }
}
