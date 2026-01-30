package ar.edu.ubp.das.ristorino_backend.repositories.reservas;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.das.ristorino_backend.components.SimpleJdbcCallFactory;
import ar.edu.ubp.das.ristorino_backend.repositories.reservas.beans.ObtenerDisponibilidadTurnosBean;

@Repository
public class ReservasRepository {
  @Autowired
  private SimpleJdbcCallFactory jdbcCallFactory;

  // GET DISPONIBILIDAD DE TURNOS (POR NRO_RESTAURANTE, SUCURSAL Y FECHA)
  public List<ObtenerDisponibilidadTurnosBean> obtenerDisponibilidadDeTurnos(Integer nroRestaurante,
      Integer nroSucursal, LocalDate fechaAReservar) {

    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("nro_restaurante", nroRestaurante)
        .addValue("nro_sucursal", nroSucursal)
        .addValue("fecha_a_reservar", fechaAReservar);

    return jdbcCallFactory.executeQuery("sp_obtener_disponibilidad_de_turnos", "dbo", params,
        "turnos_sucursales_restaurantes",
        ObtenerDisponibilidadTurnosBean.class);
  }

  // INSERT TURNO RESERVADO
  public void insertarTurnoSucursal(
      Integer nroRestaurante,
      Integer nroSucursal,
      LocalTime horaDesde,
      LocalTime horaHasta) {

    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("nro_restaurante", nroRestaurante)
        .addValue("nro_sucursal", nroSucursal)
        .addValue("hora_desde", horaDesde)
        .addValue("hora_hasta", horaHasta);

    jdbcCallFactory.execute(
        "sp_insertar_turno_sucursal",
        "dbo",
        params);
  }
}
