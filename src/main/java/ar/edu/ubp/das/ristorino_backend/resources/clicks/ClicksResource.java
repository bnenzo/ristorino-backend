package ar.edu.ubp.das.ristorino_backend.resources.clicks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.das.ristorino_backend.beans.RegistrarClickPromocionBody;
import ar.edu.ubp.das.ristorino_backend.services.clicks.ClicksService;

@RestController
@RequestMapping("/ristorino")
public class ClicksResource {
  @Autowired
  private ClicksService clicksService;

  @PostMapping("/registrar-click-promocion")
  public ResponseEntity<Void> registrarClickContenido(@RequestBody RegistrarClickPromocionBody req) {
    clicksService.registrarCostoClickContenido(req);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

}
