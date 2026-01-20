package ar.edu.ubp.das.ristorino_backend.repositories.reservas.beans;

import java.time.LocalDate;
import java.time.LocalTime;

public class ObtenerDisponibilidadTurnosBean {
  private Integer nroRestaurante;
  private Integer nroSucursal;
  private LocalDate fechaReserva;
  private LocalTime horaReserva;
  private LocalTime horaHasta;
  private Integer cantidadReservada;
  private Integer cupoDisponible;
  private Integer totalComensales;
  private Integer turnoHabilitado;
  private Integer turnoCerrado;

  public Integer getNroRestaurante() {
    return nroRestaurante;
  }

  public void setNroRestaurante(Integer nroRestaurante) {
    this.nroRestaurante = nroRestaurante;
  }

  public Integer getNroSucursal() {
    return nroSucursal;
  }

  public void setNroSucursal(Integer nroSucursal) {
    this.nroSucursal = nroSucursal;
  }

  public LocalDate getFechaReserva() {
    return fechaReserva;
  }

  public void setFechaReserva(LocalDate fechaReserva) {
    this.fechaReserva = fechaReserva;
  }

  public LocalTime getHoraReserva() {
    return horaReserva;
  }

  public void setHoraReserva(LocalTime horaReserva) {
    this.horaReserva = horaReserva;
  }

  public LocalTime getHoraHasta() {
    return horaHasta;
  }

  public void setHoraHasta(LocalTime horaHasta) {
    this.horaHasta = horaHasta;
  }

  public Integer getCantidadReservada() {
    return cantidadReservada;
  }

  public void setCantidadReservada(Integer cantidadReservada) {
    this.cantidadReservada = cantidadReservada;
  }

  public Integer getCupoDisponible() {
    return cupoDisponible;
  }

  public void setCupoDisponible(Integer cupoDisponible) {
    this.cupoDisponible = cupoDisponible;
  }

  public Integer getTotalComensales() {
    return totalComensales;
  }

  public void setTotalComensales(Integer totalComensales) {
    this.totalComensales = totalComensales;
  }

  public Integer getTurnoHabilitado() {
    return turnoHabilitado;
  }

  public void setTurnoHabilitado(Integer turnoHabilitado) {
    this.turnoHabilitado = turnoHabilitado;
  }

  public Integer getTurnoCerrado() {
    return turnoCerrado;
  }

  public void setTurnoCerrado(Integer turnoCerrado) {
    this.turnoCerrado = turnoCerrado;
  }
}
