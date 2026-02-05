package ar.edu.ubp.das.ristorino_backend.services.reservas.Clients;

import java.util.HashMap;
import java.util.Map;

import ar.edu.ubp.das.ristorino_backend.config.beans.ConfigBean;
import ar.edu.ubp.das.ristorino_backend.config.soapClient.SoapClientFactory;
import ar.edu.ubp.das.ristorino_backend.resources.reservas.beans.ActualizarReservaClienteRequestBean;
import ar.edu.ubp.das.ristorino_backend.utils.SOAPClient;

public class ReservasSoapClient {
    public void actualizarReservaCliente(ConfigBean config, ActualizarReservaClienteRequestBean request) {

        SOAPClient client = SoapClientFactory.create(
                config,
                "ActualizarReservaClienteRequest");

        Map<String, Object> params = new HashMap<>();
        params.put("body", request);

        client.callServiceForObject(Void.class,
                "", params);
    }
}
