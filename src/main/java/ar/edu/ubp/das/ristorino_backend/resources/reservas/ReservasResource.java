package ar.edu.ubp.das.ristorino_backend.resources.reservas;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.das.ristorino_backend.repositories.reservas.ReservasRepository;
import ar.edu.ubp.das.ristorino_backend.repositories.reservas.beans.ObtenerDisponibilidadTurnosBean;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/ristorino")

public class ReservasResource {

  @Autowired
  private ReservasRepository reservasRepository;

  // GET DISPONIBILIDAD DE TURNOS (POR NRO_RESTAURANTE, SUCURSAL Y FECHA)
  @GetMapping("/reservas/disponibilidad")
  public List<ObtenerDisponibilidadTurnosBean> obtenerDisponibilidadDeTurnos(
      @RequestParam Integer nroRestaurante,
      @RequestParam Integer nroSucursal,
      @RequestParam LocalDate fechaAReservar) {

    System.out.println(nroRestaurante);
    System.out.println(nroSucursal);
    System.out.println(fechaAReservar);

    return reservasRepository.obtenerDisponibilidadDeTurnos(nroRestaurante, nroSucursal, fechaAReservar);

  }

}
