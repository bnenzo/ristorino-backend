package ar.edu.ubp.das.ristorino_backend.resources.contenidos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.das.ristorino_backend.beans.ContenidoNoPublicadoBean;
import ar.edu.ubp.das.ristorino_backend.beans.PromocionContenidoBean;
import ar.edu.ubp.das.ristorino_backend.repositories.contenidos.ContenidosRepository;
import ar.edu.ubp.das.ristorino_backend.services.contenidos.ContenidosService;

@RestController
@RequestMapping("/ristorino")
public class ContenidosResource {
  @Autowired
  private ContenidosRepository contenidosRepository;
  @Autowired
  private ContenidosService contenidosService;

  @GetMapping("/promociones")
  public ResponseEntity<List<PromocionContenidoBean>> obtenerPromociones() {
    List<PromocionContenidoBean> promociones = contenidosRepository.getPromociones();
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

}
