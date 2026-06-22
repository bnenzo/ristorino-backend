package ar.edu.ubp.das.ristorino_backend.schedulers;

import ar.edu.ubp.das.ristorino_backend.beans.ClicksContenidosRestaurantesBean;
import ar.edu.ubp.das.ristorino_backend.repositories.clicks.ClicksRepository;
import ar.edu.ubp.das.ristorino_backend.services.clicks.ClicksService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificarClicksContenidosScheduler {

  @Autowired
  private ClicksService clicksService;

  @Autowired
  private ClicksRepository clicksRepository;

  // Ejecuta cada 5 minutos (300000 ms)
  // 300000 -> 5minn
  // 10000 -> 10seg
  @Scheduled(fixedRate = 15000)
  public void ejecutarNotificacionClicks() {
    try {
      // Se obtienen los clicks en las promociones no notificados
      List<ClicksContenidosRestaurantesBean> clicksSinNotificar = clicksRepository
          .obtenerClicksContenidosSinNotificar();

      // Se recorren los clicks para enviar 1 a 1 al backend del restaurante
      for (ClicksContenidosRestaurantesBean clickSinNotificar : clicksSinNotificar) {
        clicksService.registrarClickContenido(clickSinNotificar);
        clicksRepository.actualizarClickSinNotificarANotificado(clickSinNotificar);
      }

      System.out.println("Proceso de notificación ejecutado correctamente.");
    } catch (Exception e) {
      System.err.println("Error al ejecutar notificación: " + e.getMessage());
    }
  }
}
