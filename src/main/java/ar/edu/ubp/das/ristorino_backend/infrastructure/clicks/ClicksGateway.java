package ar.edu.ubp.das.ristorino_backend.infrastructure.clicks;

import ar.edu.ubp.das.ristorino_backend.infrastructure.enums.BackendType;

public class ClicksGateway {
  private final ClicksRestClient rest;
  private final ClicksSoapClient soap;

  public ClicksGateway() {
    this.rest = new ClicksRestClient();
    this.soap = new ClicksSoapClient();
  }

  public ClicksGateway(ClicksRestClient rest, ClicksSoapClient soap) {
    this.rest = rest;
    this.soap = soap;
  }

  public Void registrarClickContenido(BackendType type) {
    if (type == BackendType.SOAP) {
      soap.registrarClickContenido();
      return null;
    }
    rest.registrarClickContenido();
    return null;
  }

}
