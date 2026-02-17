package ar.edu.ubp.das.ristorino_backend.resources.contenidos;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.das.ristorino_backend.beans.ContenidoNoPublicadoBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ar.edu.ubp.das.ristorino_backend.beans.PromocionContenidoBean;
import ar.edu.ubp.das.ristorino_backend.components.AI.genIA.GenAI;
import ar.edu.ubp.das.ristorino_backend.components.AI.genIA.SystemPrompts;
import ar.edu.ubp.das.ristorino_backend.repositories.contenidos.ContenidosRepository;
import ar.edu.ubp.das.ristorino_backend.services.contenidos.ContenidosService;
import ar.edu.ubp.das.ristorino_backend.repositories.contenidos.beans.ObtenerContenidosSinContenidosIABean;
import ar.edu.ubp.das.ristorino_backend.repositories.idiomas.IdiomasRepository;
import ar.edu.ubp.das.ristorino_backend.repositories.idiomas.beans.IdiomasBean;
import ar.edu.ubp.das.ristorino_backend.resources.contenidos.beans.BuscarPromocionesIARequestBean;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/ristorino")
public class ContenidosResource {
  @Autowired
  private ContenidosRepository contenidosRepository;
  @Autowired
  private ContenidosService contenidosService;

  @Autowired
  private IdiomasRepository idiomasRepository;

  @Autowired
  private GenAI genAI;

  @GetMapping("/promociones")
  public ResponseEntity<List<PromocionContenidoBean>> obtenerPromociones(
      @RequestParam(name = "nroRestaurante", required = false) Integer nroRestaurante,
      @RequestHeader(value = "nroIdioma", required = false) Integer nroIdioma

  ) {
    List<PromocionContenidoBean> promociones = contenidosRepository.getPromociones(nroRestaurante, nroIdioma);
    System.out.println(promociones.size());
    return ResponseEntity.ok(promociones);
  }

  @GetMapping("/restaurantes/ids")
  public ResponseEntity<List<Integer>> obtenerIdsRestaurantes() {

    List<Integer> ids = contenidosRepository
        .getRestaurantesId()
        .stream()
        .map(r -> r.getNro_restaurante())
        .toList();

    return ResponseEntity.ok(ids);
  }

  // @GetMapping("/test/rest")
  // public ResponseEntity<String> testRest() {
  // contenidosService.testRestClient();
  // return ResponseEntity.ok("ok");
  // }

  // Endpoint de prueba nomas . . . borrar luego
  @GetMapping("/contenidos/no-publicados")
  public ResponseEntity<List<ContenidoNoPublicadoBean>> obtenerContenidosNoPublicados() {

    List<ContenidoNoPublicadoBean> contenidos = contenidosService.obtenerTodosLosContenidosNoPublicados();

    return ResponseEntity.ok(contenidos);
  }

  @PostMapping("/contenidos/sincronizar")
  public ResponseEntity<String> sincronizarContenidos() {

    contenidosService.sincronizarContenidosNoPublicados();

    return ResponseEntity.ok("contenidos sincronizados correctamente");
  }

  @GetMapping("/test-promociones")
  public ResponseEntity<List<ObtenerContenidosSinContenidosIABean>> obtenerContenidosSinContenidosIA()
      throws JsonProcessingException {
    List<ObtenerContenidosSinContenidosIABean> promociones = contenidosRepository.obtenerContenidosSinContenidosIA();
    List<IdiomasBean> idiomas = idiomasRepository.obtenerIdiomas();

    ObjectMapper om = new ObjectMapper();
    om.registerModule(new JavaTimeModule());
    om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    Map<String, Object> input = new LinkedHashMap<>();
    input.put("idiomas", idiomas);
    input.put("contenidos", promociones);

    String inputString = om.writeValueAsString(input);

    String text = genAI.generate(inputString, SystemPrompts.RESTAURANTE_MULTILINGUE);

    List<ObtenerContenidosSinContenidosIABean> list = om.readValue(text,
        new TypeReference<List<ObtenerContenidosSinContenidosIABean>>() {
        });

    for (ObtenerContenidosSinContenidosIABean item : list) {
      contenidosRepository.insertarContenidoGeneradoConIA(item);
    }

    return ResponseEntity.ok(list);
  }

  @PostMapping("/contenidos/busqueda")
  public ResponseEntity<List<PromocionContenidoBean>> buscarContenidosIA(
      @RequestBody BuscarPromocionesIARequestBean request,
      @RequestHeader(value = "nroIdioma", required = false) Integer nroIdioma)
      throws JsonProcessingException {
    return ResponseEntity.ok(contenidosService.buscarContenidosConIA(request, nroIdioma));
  }

}
