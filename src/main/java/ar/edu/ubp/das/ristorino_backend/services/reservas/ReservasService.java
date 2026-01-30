package ar.edu.ubp.das.ristorino_backend.services.reservas;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.ubp.das.ristorino_backend.repositories.reservas.ReservasRepository;
import ar.edu.ubp.das.ristorino_backend.resources.reservas.beans.CrearReservaRequestBean;

@Service
public class ReservasService {

  @Autowired
  private ReservasRepository reservasRepository;

  // REVISAR!
  @Transactional
  public void crearReserva(CrearReservaRequestBean request) {

    // Hora desde (viene del front)
    LocalTime horaDesde = request.getHoraDesde();

    // Hora hasta = hora desde + 1 hora
    LocalTime horaHasta = horaDesde.plusHours(1);

    // Insert en DB vía repository
    reservasRepository.insertarTurnoSucursal(
        request.getNroRestaurante(),
        request.getNroSucursal(),
        horaDesde,
        horaHasta);
  }
}
