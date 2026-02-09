package ar.edu.ubp.das.ristorino_backend.services.reservas.Clients;

import java.util.HashMap;
import java.util.Map;

import ar.edu.ubp.das.ristorino_backend.config.beans.ConfigBean;
import ar.edu.ubp.das.ristorino_backend.config.soapClient.SoapClientFactory;
import ar.edu.ubp.das.ristorino_backend.services.reservas.Dto.CrearReservaConClienteDTO;
import ar.edu.ubp.das.ristorino_backend.utils.SOAPClient;

public class ReservasSoapClient {

  public void crearReserva(ConfigBean config, CrearReservaConClienteDTO payload) {

    System.out.println(
        "[ReservasSoapClient] Enviando reserva + cliente a backend SOAP: "
            + config.getBaseUrl());

    SOAPClient client = SoapClientFactory.create(
        config,
        "CrearReservaDesdeRistorinoRequest");

    Map<String, Object> params = new HashMap<>();
    params.put("body", payload);

    client.callServiceForObject(
        Void.class,
        "",
        params);

    System.out.println(
        "[ReservasSoapClient] Reserva enviada correctamente por SOAP");
  }
}
