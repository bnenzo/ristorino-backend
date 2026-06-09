package ar.edu.ubp.das.ristorino_backend.components.ApiHandler;

import ar.edu.ubp.das.ristorino_backend.beans.SoapStringResponseBean;
import ar.edu.ubp.das.ristorino_backend.components.Httpful;
import ar.edu.ubp.das.ristorino_backend.config.beans.ConfigBean;
import ar.edu.ubp.das.ristorino_backend.config.soapClient.SoapClientFactory;
import ar.edu.ubp.das.ristorino_backend.utils.Utils;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiHandler {
  private final ConfigBean config;
  private final ApiOperationConfig operationConfig;

  private static final Gson GSON = new Gson();

  private static final Map<String, ApiOperationConfig> OPERATIONS = new HashMap<>();

  static {
    OPERATIONS.put("CrearReserva",
        new ApiOperationConfig("/reservas", "POST", "CrearReservaDesdeRistorinoRequest", ""));

    OPERATIONS.put("ObtenerContenidosNoPublicados",
        new ApiOperationConfig("/contenidos/no-publicados", "GET", "ObtenerContenidosNoPublicadosRequest",
            "ObtenerContenidosNoPublicadosResponse"));

    OPERATIONS.put("ActualizarContenidoNoPublicadoAPublicados",
        new ApiOperationConfig("/contenidos/actualizar-publicados", "POST", "ActualizarContenidosNoPublicadosRequest",
            ""));

    OPERATIONS.put("RegistrarClickContenido",
        new ApiOperationConfig("/registrar_click_contenido", "POST", "RegistrarClickContenidoRequest",
            ""));

  }

  public ApiHandler(ConfigBean config, String operation) {
    this.config = config;
    this.operationConfig = OPERATIONS.get(operation);
    if (this.operationConfig == null) {
      throw new RuntimeException("Operación no registrada: " + operation);
    }
  }

  public <T> T execute(Object payload, Class<T> responseType) {
    return switch (config.getBackendType()) {
      case "REST" -> executeRest(payload, responseType);
      case "SOAP" -> executeSoap(payload, responseType);
      default -> throw new RuntimeException("Backend type no soportado: " + config.getBackendType());
    };
  }

  public <T> T execute(Object payload, Type responseType) {
    return switch (config.getBackendType()) {
      case "REST" -> executeRest(payload, responseType);
      case "SOAP" -> executeSoap(payload, responseType);
      default -> throw new RuntimeException("Backend type no soportado: " + config.getBackendType());
    };
  }

  public void execute(Object payload) {
    execute(payload, Void.class);
  }

  public <T> T execute(Type responseType) {
    return execute(null, responseType);
  }

  // ── REST ──────────────────────────────────────────────────────────────────

  private <T> T executeRest(Object payload, Class<T> responseType) {
    Httpful http = createRestHttp(payload);
    return http.execute(responseType);
  }

  private <T> T executeRest(Object payload, Type responseType) {
    Httpful http = createRestHttp(payload);
    return http.execute(responseType);
  }

  private Httpful createRestHttp(Object payload) {
    Httpful http = new Httpful(config.getBaseUrl())
        .path(operationConfig.restPath)
        .bearer(Utils.generarToken(config.getRestSecretKey()));

    return switch (operationConfig.restMethod) {
      case "POST" -> http.post(payload);
      case "PUT" -> http.put(payload);
      case "GET" -> http;
      default -> throw new RuntimeException("HTTP method no soportado: " + operationConfig.restMethod);
    };
  }

  // ── SOAP ──────────────────────────────────────────────────────────────────

  private <T> T executeSoap(Object payload, Class<T> responseType) {
    Map<String, Object> params = buildSoapParams(payload);

    if (responseType == Void.class) {
      SoapClientFactory.create(config, operationConfig.soapOperationName)
          .callServiceForObject(Void.class, "", params);
      return null;
    }

    String jsonResponse = callSoapForJson(params);
    return GSON.fromJson(jsonResponse, responseType);
  }

  private <T> T executeSoap(Object payload, Type responseType) {
    Map<String, Object> params = buildSoapParams(payload);
    String jsonResponse = callSoapForJson(params);
    return GSON.fromJson(jsonResponse, responseType);
  }

  private Map<String, Object> buildSoapParams(Object payload) {
    Map<String, Object> params = new HashMap<>();
    if (payload != null) {
      params.put("Body", GSON.toJson(payload));
    }
    return params;
  }

  private String callSoapForJson(Map<String, Object> params) {
    List<SoapStringResponseBean> wrapper = SoapClientFactory
        .create(config, operationConfig.soapOperationName)
        .callServiceForList(SoapStringResponseBean.class, operationConfig.soapResponseWrapper, params);

    return wrapper.get(0).getResponse();
  }

}
