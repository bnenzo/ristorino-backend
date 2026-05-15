package ar.edu.ubp.das.ristorino_backend.services.clicks.Clients;

import ar.edu.ubp.das.ristorino_backend.beans.ClicksContenidosRestaurantesBean;
import ar.edu.ubp.das.ristorino_backend.config.beans.ConfigBean;

public interface ClicksBackendClient {
  void registrarClickContenido(ConfigBean config, ClicksContenidosRestaurantesBean click);
}
