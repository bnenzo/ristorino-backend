package ar.edu.ubp.das.ristorino_backend.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.das.ristorino_backend.beans.DatosRestauranteBean;
import ar.edu.ubp.das.ristorino_backend.beans.PreferenciaRestauranteBean;
import ar.edu.ubp.das.ristorino_backend.beans.PromocionContenidoBean;
import ar.edu.ubp.das.ristorino_backend.beans.RegistrarClickPromocionBody;
import ar.edu.ubp.das.ristorino_backend.beans.SucursalRestauranteBean;
import ar.edu.ubp.das.ristorino_backend.repositories.RistorinoRepository;

@RestController
@RequestMapping("/ristorino")
public class RistorinoResource {

  @Autowired
  private RistorinoRepository ristorinoRepository;

  @GetMapping("/promociones")
  public ResponseEntity<List<PromocionContenidoBean>> obtenerPromociones() {
    List<PromocionContenidoBean> promociones = ristorinoRepository.getPromociones();
    return ResponseEntity.ok(promociones);
  }

  @PostMapping("/registrar-click-promocion")
  public ResponseEntity<String> registrarClickContenido(@RequestBody RegistrarClickPromocionBody req) {
    ristorinoRepository.registrarClickContenido(req);
    return ResponseEntity.ok("Se registro el click correctamente");
  }

  @GetMapping("/notificar-clicks-promocion-manual")
  public ResponseEntity<String> notificarClicksPromocionManual() {
    ristorinoRepository.notificarClicksPromocionManual();
    return ResponseEntity.ok("Proceso de notificacion de click finalizado correctamente");
  }

  // Get para obtener info de un restaurante
  @GetMapping("/restaurante/{nro_restaurante}")
  public ResponseEntity<DatosRestauranteBean> obtenerDatosRestaurante(
      @PathVariable("nro_restaurante") Integer nroRestaurante) {

    DatosRestauranteBean datos = ristorinoRepository.getDatosRestaurante(nroRestaurante);

    if (datos == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(datos);
  }

  // Get para obtener todas las sucursales de un restaurante
  @GetMapping("/restaurante/{nro_restaurante}/sucursales")
  public ResponseEntity<List<SucursalRestauranteBean>> obtenerSucursales(
      @PathVariable("nro_restaurante") Integer nroRestaurante) {

    List<SucursalRestauranteBean> sucursales = ristorinoRepository.getSucursalesRestaurante(nroRestaurante);
    return ResponseEntity.ok(sucursales);
  }

  @GetMapping("/preferencias/{nro_restaurante}")
  public ResponseEntity<List<PreferenciaRestauranteBean>> obtenerPreferenciasRestaurante(
      @PathVariable("nro_restaurante") Integer nroRestaurante) {

    List<PreferenciaRestauranteBean> preferencias = ristorinoRepository.getPreferenciasRestaurante(nroRestaurante);

    if (preferencias == null || preferencias.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(preferencias);
  }

}
