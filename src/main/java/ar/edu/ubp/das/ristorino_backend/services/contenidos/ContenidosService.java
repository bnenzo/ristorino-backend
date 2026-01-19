package ar.edu.ubp.das.ristorino_backend.services.contenidos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.ubp.das.ristorino_backend.beans.ContenidoNoPublicadoBean;
import ar.edu.ubp.das.ristorino_backend.beans.RestauranteIdBean;
import ar.edu.ubp.das.ristorino_backend.config.beans.ConfigBean;
import ar.edu.ubp.das.ristorino_backend.repositories.configuracion.ConfiguracionRepository;
import ar.edu.ubp.das.ristorino_backend.repositories.contenidos.ContenidosRepository;
import ar.edu.ubp.das.ristorino_backend.services.contenidos.Clients.ContenidosRestClient;
import ar.edu.ubp.das.ristorino_backend.services.contenidos.Clients.ContenidosSoapClient;

@Service
public class ContenidosService {
  @Autowired
  private ConfiguracionRepository configuracionRepository;
  @Autowired
  private ContenidosRepository contenidosRepository;

  private final ContenidosRestClient rest;
  private final ContenidosSoapClient soap;

  public ContenidosService() {
    this.rest = new ContenidosRestClient();
    this.soap = new ContenidosSoapClient();
  }

  public ContenidosService(ContenidosRestClient rest, ContenidosSoapClient soap) {
    this.rest = rest;
    this.soap = soap;
  }

  public List<ContenidoNoPublicadoBean> obtenerTodosLosContenidosNoPublicados() {

    List<ContenidoNoPublicadoBean> resultado = new ArrayList<>();

    // 1) Obtenemos restaurantes desde DB ristorino
    List<RestauranteIdBean> restaurantes = contenidosRepository.getRestaurantesId();

    // 2) Recorremos cada restaurante
    for (RestauranteIdBean r : restaurantes) {

      int nroRestaurante = r.getNro_restaurante();

      // 3) Obtenemos la configuración
      ConfigBean config = configuracionRepository.obtenerConfiguracionRestaunte(nroRestaurante);

      List<ContenidoNoPublicadoBean> contenidosPorRestaurante;

      // 4) Decidir backend
      if ("SOAP".equals(config.getBackendType())) {
        System.out.println(">>> RESTAURANTE " + nroRestaurante + " ES SOAP");
        contenidosPorRestaurante = soap.obtenerContenidosNoPublicados(config);
      } else {
        System.out.println(">>> RESTAURANTE " + nroRestaurante + " ES REST");
        contenidosPorRestaurante = rest.obtenerContenidosNoPublicados(config);
      }

      // 5) Normalizar y unificar resultados
      if (contenidosPorRestaurante != null && !contenidosPorRestaurante.isEmpty()) {

        for (ContenidoNoPublicadoBean c : contenidosPorRestaurante) {

          // 🔥 PISAMOS el nro_restaurante con el de ristorino
          c.setNroRestaurante(nroRestaurante);

          resultado.add(c);
        }
      }
    }

    return resultado;
  }

  // public void testRestClient() {
  //
  // // Restaurante REST (La Bella Pizza)
  // int nroRestaurante = 1;
  //
  // ConfigBean config =
  // configuracionRepository.obtenerConfiguracionRestaunte(nroRestaurante);
  //
  // rest.obtenerContenidosNoPublicados(config);
  // }

  public void sincronizarContenidosNoPublicados() {

    List<ContenidoNoPublicadoBean> contenidos = obtenerTodosLosContenidosNoPublicados();

    for (ContenidoNoPublicadoBean c : contenidos) {

      // Obtener configuración para saber REST o SOAP
      ConfigBean config = configuracionRepository.obtenerConfiguracionRestaunte(
          c.getNroRestaurante());

      String prefijo;

      if ("SOAP".equals(config.getBackendType())) {
        prefijo = "PK";
      } else {
        prefijo = "LBP";
      }

      String codContenidoRestaurante = prefijo + "-" + c.getNroRestaurante() + "-" + c.getNroContenido();

      contenidosRepository.insertarContenidoNoPublicado(
          c,
          codContenidoRestaurante);
    }
  }

}
