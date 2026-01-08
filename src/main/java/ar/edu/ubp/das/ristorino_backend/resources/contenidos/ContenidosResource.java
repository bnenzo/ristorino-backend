package ar.edu.ubp.das.ristorino_backend.resources.contenidos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.das.ristorino_backend.beans.PromocionContenidoBean;
import ar.edu.ubp.das.ristorino_backend.repositories.contenidos.ContenidosRepository;

@RestController
@RequestMapping("/ristorino")
public class ContenidosResource {
  @Autowired
  private ContenidosRepository contenidosRepository;

  @GetMapping("/promociones")
  public ResponseEntity<List<PromocionContenidoBean>> obtenerPromociones() {
    List<PromocionContenidoBean> promociones = contenidosRepository.getPromociones();
    return ResponseEntity.ok(promociones);
  }

}
