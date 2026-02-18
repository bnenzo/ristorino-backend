package ar.edu.ubp.das.ristorino_backend.resources.reservas.beans;

import java.time.LocalDate;
import java.time.LocalTime;

public class ObtenerReservaClienteBean {
  private Integer nroReserva;
  private Integer nroRestaurante;
  private String razonSocial;
  private Integer nroSucursal;
  private String nomSucursal;
  private LocalTime horaReserva;
  private String codEstado;
  private LocalDate fechaReserva;
  private Integer cantAdultos;
  private Integer cantMenores;

  public Integer getCantMenores() {
    return cantMenores;
  }

  public void setCantMenores(Integer cantMenores) {
    this.cantMenores = cantMenores;
  }

  private String codReservaSucursal;

  public ObtenerReservaClienteBean() {
  }

  public String getCodReservaSucursal() {
    return codReservaSucursal;
  }

  public void setCodReservaSucursal(String codReservaSucursal) {
    this.codReservaSucursal = codReservaSucursal;
  }

  public Integer getCantAdultos() {
    return cantAdultos;
  }

  public void setCantAdultos(Integer cantAdultos) {
    this.cantAdultos = cantAdultos;
  }

  public LocalDate getFechaReserva() {
    return fechaReserva;
  }

  public void setFechaReserva(LocalDate fechaReserva) {
    this.fechaReserva = fechaReserva;
  }

  public Integer getNroReserva() {
    return nroReserva;
  }

  public void setNroReserva(Integer nroReserva) {
    this.nroReserva = nroReserva;
  }

  public Integer getNroRestaurante() {
    return nroRestaurante;
  }

  public void setNroRestaurante(Integer nroRestaurante) {
    this.nroRestaurante = nroRestaurante;
  }

  public String getRazonSocial() {
    return razonSocial;
  }

  public void setRazonSocial(String razonSocial) {
    this.razonSocial = razonSocial;
  }

  public Integer getNroSucursal() {
    return nroSucursal;
  }

  public void setNroSucursal(Integer nroSucursal) {
    this.nroSucursal = nroSucursal;
  }

  public String getNomSucursal() {
    return nomSucursal;
  }

  public void setNomSucursal(String nomSucursal) {
    this.nomSucursal = nomSucursal;
  }

  public LocalTime getHoraReserva() {
    return horaReserva;
  }

  public void setHoraReserva(LocalTime horaReserva) {
    this.horaReserva = horaReserva;
  }

  public String getCodEstado() {
    return codEstado;
  }

  public void setCodEstado(String codEstado) {
    this.codEstado = codEstado;
  }

  @Override
  public String toString() {
    return "ReservaClienteBean{" +
        "nroReserva=" + nroReserva +
        ", nroRestaurante=" + nroRestaurante +
        ", razonSocial='" + razonSocial + '\'' +
        ", nroSucursal=" + nroSucursal +
        ", nomSucursal='" + nomSucursal + '\'' +
        ", horaReserva=" + horaReserva +
        ", codEstado='" + codEstado + '\'' +
        '}';
  }

}
