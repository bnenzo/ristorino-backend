package ar.edu.ubp.das.ristorino_backend.services.reservas.Clients;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.ubp.das.ristorino_backend.config.beans.ConfigBean;
import ar.edu.ubp.das.ristorino_backend.config.soapClient.SoapClientFactory;
import ar.edu.ubp.das.ristorino_backend.resources.reservas.beans.ActualizarReservaClienteRequestBean;
import ar.edu.ubp.das.ristorino_backend.services.reservas.Dto.CrearReservaConClienteDTO;
import ar.edu.ubp.das.ristorino_backend.utils.SOAPClient;

@Component("SOAP-RESERVAS")
public class ReservasSoapClient implements ReservasBackendClient {

  public void crearReserva(ConfigBean config, CrearReservaConClienteDTO payload) {

    System.out.println(
        "[ReservasSoapClient] Enviando reserva + cliente a backend SOAP: " + config.getBaseUrl());

    SOAPClient client = SoapClientFactory.create(
        config,
        "CrearReservaDesdeRistorinoRequest");

    ObjectMapper mapper = new ObjectMapper();

    String stringBody = mapper.createObjectNode()

        // Cliente
        .put("nroCliente", payload.getCliente().getNroCliente())
        .put("apellido", payload.getCliente().getApellido())
        .put("nombre", payload.getCliente().getNombre())
        .put("correo", payload.getCliente().getCorreo())
        .put("telefonos", payload.getCliente().getTelefonos())

        // Reserva
        .put("codReserva", payload.getReserva().getCodReserva())
        .put("fechaReserva", payload.getReserva().getFechaReserva())
        .put("nroRestaurante", payload.getReserva().getNroRestaurante())
        .put("nroSucursal", payload.getReserva().getNroSucursal())
        .put("codZona", payload.getReserva().getCodZona())
        .put("horaReserva", payload.getReserva().getHoraReserva())
        .put("cantAdultos", payload.getReserva().getCantAdultos())
        .put("cantMenores", payload.getReserva().getCantMenores())
        .put("costoReserva", payload.getReserva().getCostoReserva())

        .toString();

    Map<String, Object> params = new HashMap<>();
    params.put("Body", stringBody);
    client.callServiceForObject(Void.class,
        "", params);

    // try {
    // ObjectMapper mapper = new ObjectMapper();
    // String jsonBody = mapper.writeValueAsString(payload);
    //
    // Map<String, Object> params = new HashMap<>();
    // params.put("Body", jsonBody);
    //
    // client.callServiceForObject(Void.class, "", params);
    //
    // System.out.println(
    // "[ReservasSoapClient] Reserva enviada correctamente por SOAP");
    //
    // } catch (JsonProcessingException e) {
    // throw new RuntimeException("Error al serializar reserva", e);
    // }
  }

  // posiblemente borrar
  public void actualizarReservaCliente(
      ConfigBean config,
      ActualizarReservaClienteRequestBean request) {

    SOAPClient client = SoapClientFactory.create(
        config,
        "ActualizarReservaClienteRequest");

    try {

      ObjectMapper mapper = new ObjectMapper();

      String jsonBody = mapper.writeValueAsString(request);

      Map<String, Object> params = new HashMap<>();
      params.put("Body", jsonBody);

      client.callServiceForObject(
          Void.class,
          "",
          params);

    } catch (JsonProcessingException e) {
      throw new RuntimeException("Error al serializar actualización de reserva", e);
    }
  }
}