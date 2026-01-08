package ar.edu.ubp.das.ristorino_backend.schedulers;

import ar.edu.ubp.das.ristorino_backend.repositories.clicks.ClicksRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificarClicksContenidosScheduler {

  @Autowired
  private ClicksRepository clicksRepository;

  // Ejecuta cada 5 minutos (300000 ms)
  // 300000 -> 5min
  // 10000 -> 10seg
  @Scheduled(fixedRate = 10000)
  public void ejecutarNotificacionClicks() {
    try {
      clicksRepository.notificarClicksPromocionManual();
      System.out.println("Proceso de notificación ejecutado correctamente.");
    } catch (Exception e) {
      System.err.println("Error al ejecutar notificación: " + e.getMessage());
    }
  }
}
