package ar.edu.ubp.das.ristorino_backend.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.das.ristorino_backend.beans.PromocionContenidoBean;
import ar.edu.ubp.das.ristorino_backend.beans.ProvinciaBean;
import ar.edu.ubp.das.ristorino_backend.beans.RegistrarClickPromocionBody;
import ar.edu.ubp.das.ristorino_backend.repositories.RistorinoRepository;

@RestController
@RequestMapping("/ristorino")
public class RistorinoResource {

  @Autowired
  private RistorinoRepository ristorinoRepository;

  @GetMapping("/provincias")
  public ResponseEntity<List<ProvinciaBean>> obtenerProvincias() {
    List<ProvinciaBean> provincias = ristorinoRepository.getPaises();
    return ResponseEntity.ok(provincias);
  }

  @GetMapping("/promociones")
  public ResponseEntity<List<PromocionContenidoBean>> obtenerPromociones() {
    List<PromocionContenidoBean> promociones = ristorinoRepository.getPromociones();
    return ResponseEntity.ok(promociones);
  }

  @PostMapping("/registrar-click-promocion")
  public ResponseEntity<Integer> registrarClickContenido(@RequestBody RegistrarClickPromocionBody req) {
    Integer idClick = ristorinoRepository.registrarClickContenido(req);
    return ResponseEntity.ok(idClick);
  }
}
