package ar.edu.ubp.das.ristorino_backend.resources.reservas.beans;

import java.time.LocalDate;
import java.time.LocalTime;

public class CrearReservaRequestBean {

  private Integer nroRestaurante;
  private Integer nroSucursal;
  private LocalDate fechaReserva;
  private LocalTime horaReserva;
  private Integer cantAdultos;
  private Integer cantMenores;
  private String codZona;

  public CrearReservaRequestBean() {
  }

  public void setCodZona(String codZona) {
    this.codZona = codZona;
  }

  public String getCodZona() {
    return codZona;
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

  public LocalTime getHoraReserva() {
    return horaReserva;
  }

  public void setHoraReserva(LocalTime horaReserva) {
    this.horaReserva = horaReserva;
  }

  public Integer getCantAdultos() {
    return cantAdultos;
  }

  public void setCantAdultos(Integer cantAdultos) {
    this.cantAdultos = cantAdultos;
  }

  public Integer getCantMenores() {
    return cantMenores;
  }

  public void setCantMenores(Integer cantMenores) {
    this.cantMenores = cantMenores;
  }
}
