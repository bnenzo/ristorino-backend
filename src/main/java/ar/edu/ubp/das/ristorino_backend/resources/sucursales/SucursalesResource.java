package ar.edu.ubp.das.ristorino_backend.resources.sucursales;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.das.ristorino_backend.repositories.sucursales.SucursalesRepository;
import ar.edu.ubp.das.ristorino_backend.repositories.sucursales.beans.SucursalesBean;

@RestController
@RequestMapping("/ristorino")
public class SucursalesResource {

  @Autowired
  private SucursalesRepository sucursalesRepository;

  // GET SUCURSALES DE UN RESTAURANTE (POR NRO_RESTAURANTE)
  @GetMapping("/sucursales/{nro_restaurante}")
  public ResponseEntity<List<SucursalesBean>> obtenerSucursalesRestaurante(
      @PathVariable("nro_restaurante") Integer nroRestaurante) {

    return ResponseEntity.ok(sucursalesRepository.obtenerSucursalesRestaurante(nroRestaurante));
  }
}
