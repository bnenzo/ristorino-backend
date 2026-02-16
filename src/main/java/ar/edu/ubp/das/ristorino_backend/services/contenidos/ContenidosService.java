package ar.edu.ubp.das.ristorino_backend.services.contenidos;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ar.edu.ubp.das.ristorino_backend.beans.ContenidoNoPublicadoBean;
import ar.edu.ubp.das.ristorino_backend.beans.RestauranteIdBean;
import ar.edu.ubp.das.ristorino_backend.components.AI.genIA.GenAI;
import ar.edu.ubp.das.ristorino_backend.components.AI.genIA.SystemPrompts;
import ar.edu.ubp.das.ristorino_backend.config.beans.ConfigBean;
import ar.edu.ubp.das.ristorino_backend.repositories.configuracion.ConfiguracionRepository;
import ar.edu.ubp.das.ristorino_backend.repositories.contenidos.ContenidosRepository;
import ar.edu.ubp.das.ristorino_backend.repositories.contenidos.beans.ObtenerContenidosSinContenidosIABean;
import ar.edu.ubp.das.ristorino_backend.repositories.idiomas.IdiomasRepository;
import ar.edu.ubp.das.ristorino_backend.repositories.idiomas.beans.IdiomasBean;
import ar.edu.ubp.das.ristorino_backend.services.contenidos.Clients.ContenidosRestClient;
import ar.edu.ubp.das.ristorino_backend.services.contenidos.Clients.ContenidosSoapClient;
import ar.edu.ubp.das.ristorino_backend.services.contenidos.Dto.ActualizarContenidosNoPublicadosDTO;

@Service
public class ContenidosService {
  @Autowired
  private ConfiguracionRepository configuracionRepository;
  @Autowired
  private ContenidosRepository contenidosRepository;
  @Autowired
  private IdiomasRepository idiomasRepository;

  @Autowired
  private GenAI genAI;

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

  public void sincronizarContenidosNoPublicados() {

    List<ContenidoNoPublicadoBean> contenidos = obtenerTodosLosContenidosNoPublicados();

    for (ContenidoNoPublicadoBean c : contenidos) {

      // Obtener configuración para saber REST o SOAP
      ConfigBean config = configuracionRepository.obtenerConfiguracionRestaunte(
          c.getNroRestaurante());

      String codContenidoRestaurante = config.getPrefix() + "-" + c.getNroRestaurante() + "-" + c.getNroContenido();

      contenidosRepository.insertarContenidoNoPublicado(
          c,
          codContenidoRestaurante);
    }

    this.actualizarContenidoNoPublicadosAPublicados(contenidos);

  }

  public List<ObtenerContenidosSinContenidosIABean> generarContenidosIA()
      throws JsonProcessingException {
    List<ObtenerContenidosSinContenidosIABean> promociones = contenidosRepository.obtenerContenidosSinContenidosIA();
    List<IdiomasBean> idiomas = idiomasRepository.obtenerIdiomas();

    int chunkSize = 3;
    List<ObtenerContenidosSinContenidosIABean> resultAll = new ArrayList<>();

    for (int i = 0; i < promociones.size(); i += chunkSize) {
      List<ObtenerContenidosSinContenidosIABean> chunk = promociones.subList(i,
          Math.min(i + chunkSize, promociones.size()));

      ObjectMapper om = new ObjectMapper();
      om.registerModule(new JavaTimeModule());
      om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

      Map<String, Object> input = new LinkedHashMap<>();
      input.put("idiomas", idiomas);
      input.put("contenidos", chunk);

      String inputString = om.writeValueAsString(input);

      String text = genAI.generate(inputString, SystemPrompts.RESTAURANTE_MULTILINGUE);

      List<ObtenerContenidosSinContenidosIABean> list = om.readValue(text,
          new TypeReference<List<ObtenerContenidosSinContenidosIABean>>() {
          });

      for (ObtenerContenidosSinContenidosIABean item : list) {
        contenidosRepository.insertarContenidoGeneradoConIA(item);
      }

      resultAll.addAll(list);

    }

    return resultAll;
  }

  // ACTUALIZAR LOS CONTENIDOS NO PUBLICADOS A PUBLICADOS
  public void actualizarContenidoNoPublicadosAPublicados(List<ContenidoNoPublicadoBean> contenidos) {
    Map<Integer, List<ContenidoNoPublicadoBean>> contenidosPorRestaurante = contenidos.stream()
        .collect(Collectors.groupingBy(ContenidoNoPublicadoBean::getNroRestaurante));

    for (Map.Entry<Integer, List<ContenidoNoPublicadoBean>> entry : contenidosPorRestaurante.entrySet()) {

      Integer nroRestaurante = entry.getKey();
      List<ContenidoNoPublicadoBean> listaContenidos = entry.getValue();

      ConfigBean config = configuracionRepository.obtenerConfiguracionRestaunte(nroRestaurante);

      ActualizarContenidosNoPublicadosDTO actualizarContenidosNoPublicadosBody = new ActualizarContenidosNoPublicadosDTO();
      actualizarContenidosNoPublicadosBody.setContenidos(listaContenidos);

      if ("SOAP".equals(config.getBackendType())) {
        soap.actualizarContenidoNoPublicadosAPublicados(config, actualizarContenidosNoPublicadosBody);
      } else {
        rest.actualizarContenidoNoPublicadosAPublicados(config, actualizarContenidosNoPublicadosBody);
      }
    }
  }

}
