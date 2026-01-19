package ar.edu.ubp.das.ristorino_backend.resources.restaurantes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.das.ristorino_backend.beans.DatosRestauranteBean;
import ar.edu.ubp.das.ristorino_backend.beans.PreferenciaRestauranteBean;
import ar.edu.ubp.das.ristorino_backend.beans.SucursalRestauranteBean;
import ar.edu.ubp.das.ristorino_backend.repositories.restaurantes.RestaurantesRepository;

@RestController
@RequestMapping("/ristorino")
public class RestaurantesResource {
  @Autowired
  private RestaurantesRepository restaurantesRepository;

  // Get para obtener info de un restaurante
  @GetMapping("/restaurante/{nro_restaurante}")
  public ResponseEntity<DatosRestauranteBean> obtenerDatosRestaurante(
      @PathVariable("nro_restaurante") Integer nroRestaurante) {

    DatosRestauranteBean datos = restaurantesRepository.getDatosRestaurante(nroRestaurante);

    if (datos == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(datos);
  }

  // Get para obtener todas las sucursales de un restaurante
  @GetMapping("/restaurante/{nro_restaurante}/sucursales")
  public ResponseEntity<List<SucursalRestauranteBean>> obtenerSucursales(
      @PathVariable("nro_restaurante") Integer nroRestaurante) {

    List<SucursalRestauranteBean> sucursales = restaurantesRepository.getSucursalesRestaurante(nroRestaurante);
    return ResponseEntity.ok(sucursales);
  }

  @GetMapping("/preferencias/{nro_restaurante}")
  public ResponseEntity<List<PreferenciaRestauranteBean>> obtenerPreferenciasRestaurante(
      @PathVariable("nro_restaurante") Integer nroRestaurante) {

    List<PreferenciaRestauranteBean> preferencias = restaurantesRepository.getPreferenciasRestaurante(nroRestaurante);

    if (preferencias == null || preferencias.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(preferencias);
  }
}
