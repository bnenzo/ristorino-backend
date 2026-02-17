package ar.edu.ubp.das.ristorino_backend.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import ar.edu.ubp.das.ristorino_backend.repositories.RistorinoRepository;
import ar.edu.ubp.das.ristorino_backend.repositories.contenidos.beans.ObtenerContenidosSinContenidosIABean;
import ar.edu.ubp.das.ristorino_backend.services.contenidos.ContenidosService;

@RestController
@RequestMapping("/ristorino")
public class RistorinoResource {

  @Autowired
  private ContenidosService contenidosService;
  @Autowired
  private RistorinoRepository ristorinoRepository;

  // =====================================
  // Obtener promociones con IA
  // =====================================
  @PostMapping("/popular-contenido-ia")
  public ResponseEntity<List<ObtenerContenidosSinContenidosIABean>> popularPromociones()
      throws JsonProcessingException {
    contenidosService.sincronizarContenidosNoPublicados();
    return ResponseEntity.ok(contenidosService.generarContenidosIA());
  }
}
