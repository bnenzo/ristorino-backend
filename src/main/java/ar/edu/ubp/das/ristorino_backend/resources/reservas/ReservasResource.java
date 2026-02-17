package ar.edu.ubp.das.ristorino_backend.resources.reservas;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.das.ristorino_backend.repositories.reservas.ReservasRepository;
import ar.edu.ubp.das.ristorino_backend.repositories.reservas.beans.ObtenerDisponibilidadTurnosBean;
import ar.edu.ubp.das.ristorino_backend.resources.reservas.beans.ActualizarReservaClienteRequestBean;
import ar.edu.ubp.das.ristorino_backend.resources.reservas.beans.CrearReservaRequestBean;
import ar.edu.ubp.das.ristorino_backend.resources.reservas.beans.ObtenerReservaClienteBean;
import ar.edu.ubp.das.ristorino_backend.services.reservas.ReservasService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ar.edu.ubp.das.ristorino_backend.repositories.reservas.beans.ObtenerEstadosIdiomaBean;
import ar.edu.ubp.das.ristorino_backend.repositories.reservas.beans.ObtenerReservasClienteBean;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/ristorino")

public class ReservasResource {

  @Autowired
  private ReservasRepository reservasRepository;
  @Autowired
  private ReservasService reservasService;

  // GET DISPONIBILIDAD DE TURNOS (POR NRO_RESTAURANTE, SUCURSAL Y FECHA)
  @GetMapping("/reservas/disponibilidad")
  public List<ObtenerDisponibilidadTurnosBean> obtenerDisponibilidadDeTurnos(
      @RequestParam Integer nroRestaurante,
      @RequestParam Integer nroSucursal,
      @RequestParam LocalDate fechaAReservar) {

    return reservasRepository.obtenerDisponibilidadDeTurnos(nroRestaurante, nroSucursal, fechaAReservar);
  }

  // =====================================
  // REALIZAR UNA RESERVA EN UNA SUCURSAL
  // =====================================
  @PostMapping("/reservas")
  public ResponseEntity<String> crearReserva(
      @RequestHeader(value = "nroCliente") Integer nroCliente,
      @RequestBody CrearReservaRequestBean request) {

    String codigoReserva = reservasService.crearReserva(request, nroCliente);
    return ResponseEntity.ok("\"" + codigoReserva + "\"");
  }

  // GET LAS RESERVAS DE UN CLIENTE
  @GetMapping("/reservas/cliente")
  public List<ObtenerReservasClienteBean> obtenerReservasCliente(
      @RequestHeader(value = "nroCliente") Integer nroCliente,
      @RequestHeader(value = "nroIdioma", required = false) Integer nroIdioma,
      @RequestParam(value = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
      @RequestParam(value = "estados", required = false) List<String> estados) {
    return reservasRepository.obtenerReservasCliente(nroCliente, nroIdioma, fecha, estados);
  }

  // OBTENER LOS ESTADOS POSIBLES DE UNA RESERVA EN UN IDIOMA EN PARTICULAR
  @GetMapping("/reservas/estados")
  public List<ObtenerEstadosIdiomaBean> obtenerEstadosDeReservaPorIdioma(
      @RequestHeader(value = "nroIdioma", required = false) Integer nroIdioma) {
    return reservasRepository.obtenerEstadosDeReservaPorIdioma(nroIdioma);
  }

  // OBTENER LA RESERVA DE UN CLIENTE
  @GetMapping("/reservas/cliente/{nro_reserva}")
  public ObtenerReservaClienteBean obtenerReservaCliente(
      @PathVariable("nro_reserva") Integer nroReserva,
      @RequestHeader(value = "nroCliente") Integer nroCliente) {
    return reservasRepository.obtenerReservaCliente(nroCliente, nroReserva);
  }

  // ACTUALIZAR LA RESERVA DE UN CLIENTE
  @PutMapping("/reservas/cliente/{nro_reserva}")
  public ResponseEntity<Void> actualizarReservaCliente(
      @PathVariable("nro_reserva") Integer nroReserva,
      @RequestBody ActualizarReservaClienteRequestBean body,
      @RequestHeader(value = "nroCliente") Integer nroCliente) {
    reservasService.actualizarReservaCliente(nroCliente, nroReserva, body);
    return ResponseEntity.ok().build();
  }

}
