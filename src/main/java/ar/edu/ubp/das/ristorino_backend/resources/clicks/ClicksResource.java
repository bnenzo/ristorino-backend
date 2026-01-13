package ar.edu.ubp.das.ristorino_backend.resources.clicks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.das.ristorino_backend.beans.RegistrarClickPromocionBody;
import ar.edu.ubp.das.ristorino_backend.repositories.clicks.ClicksRepository;

@RestController
@RequestMapping("/ristorino")
public class ClicksResource {
  @Autowired
  private ClicksRepository clicksRepository;

  @PostMapping("/registrar-click-promocion")
  public ResponseEntity<String> registrarClickContenido(@RequestBody RegistrarClickPromocionBody req) {
    clicksRepository.registrarClickContenido(req);
    return ResponseEntity.ok("Se registro el click correctamente");
  }

}
