package ar.edu.ubp.das.ristorino_backend.repositories.reservas.beans;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ObtenerReservasClienteBean {

  private Integer nroCliente;
  private Integer nroReserva;
  private LocalDateTime fechaHoraRegistro;
  private LocalDate fechaReserva;

  private Integer nroRestaurante;
  private String razonSocial;

  private Integer nroSucursal;
  private String nomSucursal;
  private String calle;
  private Integer nroCalle;
  private String barrio;
  private String codPostal;
  private String telefonosSucursal;

  private String codZona;
  private LocalTime horaReserva;

  private Integer cantAdultos;
  private Integer cantMenores;

  private String codEstado;
  private String estado;

  private LocalDate fechaCancelacion;
  private BigDecimal costoReserva;

  private String codReservaSucursal;

  public ObtenerReservasClienteBean() {
  }

  public Integer getNroCliente() {
    return nroCliente;
  }

  public void setNroCliente(Integer nroCliente) {
    this.nroCliente = nroCliente;
  }

  public Integer getNroReserva() {
    return nroReserva;
  }

  public void setNroReserva(Integer nroReserva) {
    this.nroReserva = nroReserva;
  }

  public LocalDateTime getFechaHoraRegistro() {
    return fechaHoraRegistro;
  }

  public void setFechaHoraRegistro(LocalDateTime fechaHoraRegistro) {
    this.fechaHoraRegistro = fechaHoraRegistro;
  }

  public LocalDate getFechaReserva() {
    return fechaReserva;
  }

  public void setFechaReserva(LocalDate fechaReserva) {
    this.fechaReserva = fechaReserva;
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

  public String getCalle() {
    return calle;
  }

  public void setCalle(String calle) {
    this.calle = calle;
  }

  public Integer getNroCalle() {
    return nroCalle;
  }

  public void setNroCalle(Integer nroCalle) {
    this.nroCalle = nroCalle;
  }

  public String getBarrio() {
    return barrio;
  }

  public void setBarrio(String barrio) {
    this.barrio = barrio;
  }

  public String getCodPostal() {
    return codPostal;
  }

  public void setCodPostal(String codPostal) {
    this.codPostal = codPostal;
  }

  public String getTelefonosSucursal() {
    return telefonosSucursal;
  }

  public void setTelefonosSucursal(String telefonosSucursal) {
    this.telefonosSucursal = telefonosSucursal;
  }

  public String getCodZona() {
    return codZona;
  }

  public void setCodZona(String codZona) {
    this.codZona = codZona;
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

  public String getCodEstado() {
    return codEstado;
  }

  public void setCodEstado(String codEstado) {
    this.codEstado = codEstado;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public LocalDate getFechaCancelacion() {
    return fechaCancelacion;
  }

  public void setFechaCancelacion(LocalDate fechaCancelacion) {
    this.fechaCancelacion = fechaCancelacion;
  }

  public BigDecimal getCostoReserva() {
    return costoReserva;
  }

  public void setCostoReserva(BigDecimal costoReserva) {
    this.costoReserva = costoReserva;
  }

  public String getCodReservaSucursal() {
    return codReservaSucursal;
  }

  public void setCodReservaSucursal(String codReservaSucursal) {
    this.codReservaSucursal = codReservaSucursal;
  }
}