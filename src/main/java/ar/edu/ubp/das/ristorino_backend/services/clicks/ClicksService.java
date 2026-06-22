package ar.edu.ubp.das.ristorino_backend.services.clicks;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.edu.ubp.das.ristorino_backend.beans.ClicksContenidosRestaurantesBean;
import ar.edu.ubp.das.ristorino_backend.beans.ClienteBean;
import ar.edu.ubp.das.ristorino_backend.beans.clicks.RegistrarClickPromocionRequest;
import ar.edu.ubp.das.ristorino_backend.components.ApiHandler.ApiHandler;
import ar.edu.ubp.das.ristorino_backend.config.beans.ConfigBean;
import ar.edu.ubp.das.ristorino_backend.repositories.clicks.ClicksRepository;
import ar.edu.ubp.das.ristorino_backend.repositories.clientes.ClienteRepository;
import ar.edu.ubp.das.ristorino_backend.repositories.configuracion.ConfiguracionRepository;
import ar.edu.ubp.das.ristorino_backend.repositories.costos.CostosRepository;
import com.google.gson.JsonObject;

@Service
public class ClicksService {
  @Autowired
  private ConfiguracionRepository configuracionRepository;

  @Autowired
  private ClicksRepository clicksRepository;

  @Autowired
  private ClienteRepository clienteRepository;

  public ClicksService() {
  }

  public Void registrarClickContenido(ClicksContenidosRestaurantesBean clickContenido) {
    ConfigBean config = configuracionRepository.obtenerConfiguracionRestaunte(clickContenido.getNroRestaurante());

    // Obtenemos todo el cliente
    ClienteBean cliente = null;

    if (clickContenido.getNroCliente() != null) {
      cliente = clienteRepository.obtenerClientePorId(
          clickContenido.getNroCliente());
    }

    JsonObject body = new JsonObject();
    body.addProperty("nroRestaurante", 1);
    body.addProperty("nroContenido", clickContenido.getNroContenido());
    body.addProperty("nroClick", clickContenido.getNroClick());
    body.addProperty("fechaHoraRegistro", clickContenido.getFechaHoraRegistro());
    body.addProperty("nroCliente", cliente != null ? cliente.getNroCliente() : null);
    body.addProperty("costoClick", clickContenido.getCostoClick());
    body.addProperty("apellido", cliente != null ? cliente.getApellido() : null);
    body.addProperty("nombre", cliente != null ? cliente.getNombre() : null);
    body.addProperty("correo", cliente != null ? cliente.getCorreo() : null);
    body.addProperty("telefonos", cliente != null ? cliente.getTelefonos() : null);

    new ApiHandler(config, "RegistrarClickContenido").execute(body);

    return null;
  }

  public void notificarClicksPromocionManual() {
    // Se obtienen los clicks en las promociones no notificados
    List<ClicksContenidosRestaurantesBean> clicksSinNotificar = clicksRepository.obtenerClicksContenidosSinNotificar();

    // Se recorren los clicks para enviar 1 a 1 al backend del restaurante
    for (ClicksContenidosRestaurantesBean clickSinNotificar : clicksSinNotificar) {
      this.registrarClickContenido(clickSinNotificar);
      clicksRepository.actualizarClickSinNotificarANotificado(clickSinNotificar);
    }
  }

}
