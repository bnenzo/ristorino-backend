package ar.edu.ubp.das.ristorino_backend.resources.reservas.beans;

import java.time.LocalDate;
import java.time.LocalTime;

public class CrearReservaRequestBean {

  private Integer nroRestaurante;
  private Integer nroSucursal;
  private LocalDate fechaReserva;
  private LocalTime horaDesde;
  private Integer personas;

  public CrearReservaRequestBean() {
  }

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

  public LocalTime getHoraDesde() {
    return horaDesde;
  }

  public void setHoraDesde(LocalTime horaDesde) {
    this.horaDesde = horaDesde;
  }

  public Integer getPersonas() {
    return personas;
  }

  public void setPersonas(Integer personas) {
    this.personas = personas;
  }
}
