package ar.edu.ubp.das.ristorino_backend.services.clicks;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.edu.ubp.das.ristorino_backend.beans.ClicksContenidosRestaurantesBean;
import ar.edu.ubp.das.ristorino_backend.beans.RegistrarClickPromocionBody;
import ar.edu.ubp.das.ristorino_backend.components.ApiHandler.ApiHandler;
import ar.edu.ubp.das.ristorino_backend.config.beans.ConfigBean;
import ar.edu.ubp.das.ristorino_backend.repositories.clicks.ClicksRepository;
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
  private CostosRepository costosRepository;

  public ClicksService() {
  }

  public Void registrarClickContenido(ClicksContenidosRestaurantesBean clickContenido) {
    ConfigBean config = configuracionRepository.obtenerConfiguracionRestaunte(clickContenido.getNroRestaurante());

    JsonObject body = new JsonObject();
    body.addProperty("nroRestaurante", 1);
    body.addProperty("nroContenido", clickContenido.getNroContenido());
    body.addProperty("nroClick", clickContenido.getNroClick());
    body.addProperty("fechaHoraRegistro", clickContenido.getFechaHoraRegistro());
    body.addProperty("nroCliente", 1);
    body.addProperty("costoClick", clickContenido.getCostoClick());

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

  public void registrarCostoClickContenido(RegistrarClickPromocionBody body) {

    BigDecimal costoClick = costosRepository
        .obtenerCostoPorTipo("CLICK")
        .getMonto();

    body.setCostoClick(costoClick);

    clicksRepository.registrarClickContenido(body);
  }
}
