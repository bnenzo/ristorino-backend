package ar.edu.ubp.das.ristorino_backend.services.clicks;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.edu.ubp.das.ristorino_backend.beans.ClicksContenidosRestaurantesBean;
import ar.edu.ubp.das.ristorino_backend.config.beans.ConfigBean;
import ar.edu.ubp.das.ristorino_backend.repositories.clicks.ClicksRepository;
import ar.edu.ubp.das.ristorino_backend.repositories.configuracion.ConfiguracionRepository;
import ar.edu.ubp.das.ristorino_backend.services.clicks.Clients.ClicksRestClient;
import ar.edu.ubp.das.ristorino_backend.services.clicks.Clients.ClicksSoapClient;

@Service
public class ClicksService {
  @Autowired
  private ConfiguracionRepository configuracionRepository;

  @Autowired
  private ClicksRepository clicksRepository;

  private final ClicksRestClient rest;
  private final ClicksSoapClient soap;

  public ClicksService() {
    this.rest = new ClicksRestClient();
    this.soap = new ClicksSoapClient();
  }

  public ClicksService(ClicksRestClient rest, ClicksSoapClient soap) {
    this.rest = rest;
    this.soap = soap;
  }

  public Void registrarClickContenido(ClicksContenidosRestaurantesBean clickContenido) {
    ConfigBean config = configuracionRepository.obtenerConfiguracionRestaunte(clickContenido.getNroRestaurante());
    if ("SOAP".equals(config.getBackendType())) {
      soap.registrarClickContenido(config, clickContenido);
      return null;
    }
    rest.registrarClickContenido(config, clickContenido);
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
