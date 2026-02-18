package ar.edu.ubp.das.ristorino_backend.repositories.reservas;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.das.ristorino_backend.components.SimpleJdbcCallFactory;
import ar.edu.ubp.das.ristorino_backend.repositories.reservas.beans.ObtenerDisponibilidadTurnosBean;
import ar.edu.ubp.das.ristorino_backend.repositories.reservas.beans.ObtenerEstadosIdiomaBean;
import ar.edu.ubp.das.ristorino_backend.repositories.reservas.beans.ObtenerReservasClienteBean;
import ar.edu.ubp.das.ristorino_backend.resources.reservas.beans.ActualizarReservaClienteRequestBean;
import ar.edu.ubp.das.ristorino_backend.resources.reservas.beans.ObtenerReservaClienteBean;

@Repository
public class ReservasRepository {
  @Autowired
  private SimpleJdbcCallFactory jdbcCallFactory;

  // =====================================================================
  // GET DISPONIBILIDAD DE TURNOS (POR NRO_RESTAURANTE, SUCURSAL Y FECHA)
  // =====================================================================
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

  // =====================================
  // REALIZAR UNA RESERVA EN UNA SUCURSAL
  // =====================================
  public void crearReservaRestaurante(
      Integer nroCliente,
      LocalDate fechaReserva,
      Integer nroRestaurante,
      Integer nroSucursal,
      String codZona,
      LocalTime horaReserva,
      Integer cantAdultos,
      Integer cantMenores,
      String codEstado,
      Double costoReserva,
      String codReservaSucursal) {

    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("nro_cliente", nroCliente)
        .addValue("fecha_reserva", fechaReserva)
        .addValue("nro_restaurante", nroRestaurante)
        .addValue("nro_sucursal", nroSucursal)
        .addValue("cod_zona", codZona)
        .addValue("hora_reserva", horaReserva)
        .addValue("cant_adultos", cantAdultos)
        .addValue("cant_menores", cantMenores)
        .addValue("cod_estado", codEstado)
        .addValue("costo_reserva", costoReserva)
        .addValue("cod_reserva_sucursal", codReservaSucursal);

    jdbcCallFactory.execute(
        "sp_crear_reserva_restaurante",
        "dbo",
        params);
  }

  // GET LAS RESERVAS DE UN CLIENTE
  public List<ObtenerReservasClienteBean> obtenerReservasCliente(Integer nroCliente, Integer nroIdioma,
      LocalDate fecha, List<String> estados) {

    String estadosCsv = (estados == null || estados.isEmpty())
        ? null
        : String.join(",", estados);

    System.out.println(estadosCsv);
    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("nro_cliente", nroCliente)
        .addValue("nro_idioma", nroIdioma)
        .addValue("estados_csv", estadosCsv)
        .addValue("fecha_reserva", fecha);

    return jdbcCallFactory.executeQuery("sp_get_reservas_por_cliente", "dbo", params,
        "turnos_sucursales_restaurantes",
        ObtenerReservasClienteBean.class);
  }

  // OBTENER LOS ESTADOS POSIBLES DE UNA RESERVA EN UN IDIOMA EN PARTICULAR
  public List<ObtenerEstadosIdiomaBean> obtenerEstadosDeReservaPorIdioma(Integer nroIdioma) {
    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("nro_idioma", nroIdioma);

    return jdbcCallFactory.executeQuery("sp_get_estados_reserva_por_idioma", "dbo", params,
        "turnos_sucursales_restaurantes",
        ObtenerEstadosIdiomaBean.class);
  }

  // OBTENER LA RESERVA DE UN CLIENTE
  public ObtenerReservaClienteBean obtenerReservaCliente(Integer nroCliente, Integer nroReserva) {
    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("nro_cliente", nroCliente)
        .addValue("nro_reserva", nroReserva);

    return jdbcCallFactory.executeQuery("sp_get_reserva_cliente", "dbo", params,
        "reservas_restaurantes",
        ObtenerReservaClienteBean.class).get(0);
  }

  // ACTUALIZAR LA RESERVA DE UN CLIENTE
  public void actualizarReservaCliente(Integer nroCliente, Integer nroReserva,
      ActualizarReservaClienteRequestBean body) {
    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("nro_cliente", nroCliente)
        .addValue("nro_reserva", nroReserva)
        .addValue("cant_adultos", body.getCantAdultos())
        .addValue("cant_menores", body.getCantMenores())
        .addValue("fecha_reserva", body.getFechaReserva())
        .addValue("hora_reserva", body.getHoraReserva())
        .addValue("fecha_cancelacion", body.getFechaCancelacion())
        .addValue("cod_estado", body.getFechaCancelacion() != null ? "CAN" : body.getCodEstado());

    jdbcCallFactory.executeWithOutputs(
        "sp_actualizar_reserva_cliente", "dbo", params);
  }
}
