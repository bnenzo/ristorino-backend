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

}
