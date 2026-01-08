package ar.edu.ubp.das.ristorino_backend.infrastructure.clicks;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import ar.edu.ubp.das.ristorino_backend.beans.ClicksContenidosRestaurantesBean;
import ar.edu.ubp.das.ristorino_backend.utils.SOAPClient;

public class ClicksSoapClient {

  public void registrarClickContenido() {
    SOAPClient client = SOAPClient.SOAPClientBuilder
        .fromConfig("""
             {
             "wsdlUrl":"http://localhost:8087/services/perukai.wsdl",
             "namespace": "http://services.perukai.das.ubp.edu.ar/",
             "serviceName": "PerukaiWSPortService",
            "portName": "PerukaiWSPortSoap11",
            "username": "",
            "password": ""
             }
            """)
        .operationName("RegistrarClickContenidoRequest")
        .build();

    ClicksContenidosRestaurantesBean clickContenidoRestaurante = new ClicksContenidosRestaurantesBean();
    clickContenidoRestaurante.setCostoClick(BigDecimal.valueOf(1));
    clickContenidoRestaurante.setFechaHoraRegistro("2026-01-07T10:30:00");
    clickContenidoRestaurante.setNroClick(1);
    clickContenidoRestaurante.setNroCliente(1);
    clickContenidoRestaurante.setNroContenido(1);
    clickContenidoRestaurante.setNroIdioma(1);
    clickContenidoRestaurante.setNroRestaurante(1);

    Map<String, Object> params = new HashMap<>();
    params.put("body", clickContenidoRestaurante);

    client.callServiceForObject(Void.class,
        "", params);
  }
}
