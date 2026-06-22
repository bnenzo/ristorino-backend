package ar.edu.ubp.das.ristorino_backend.resources.clicks;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.das.ristorino_backend.beans.clicks.RegistrarClickPromocionRequest;
import ar.edu.ubp.das.ristorino_backend.repositories.clicks.ClicksRepository;
import ar.edu.ubp.das.ristorino_backend.repositories.costos.CostosRepository;
import ar.edu.ubp.das.ristorino_backend.services.clicks.ClicksService;

@RestController
@RequestMapping("/ristorino")
public class ClicksResource {
  @Autowired
  private ClicksService clicksService;

  @Autowired
  private CostosRepository costosRepository;

  @Autowired
  private ClicksRepository clicksRepository;

  /*
   * ------------------------
   * REGISTRAR CLICK EN UN CONTENIDO -> Se llama cuando el usuario hace click a un
   * contenido
   * ------------------------
   */
  @PostMapping("/registrar-click-promocion")
  public ResponseEntity<Void> registrarClickContenido(
      @RequestHeader(value = "nroCliente", required = false) Integer nroCliente,
      @RequestBody RegistrarClickPromocionRequest req) {
    BigDecimal costoClick = costosRepository
        .obtenerCostoPorTipo("CLICK")
        .getMonto();

    req.setCostoClick(costoClick);
    clicksRepository.registrarClickContenido(req, nroCliente);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

}
