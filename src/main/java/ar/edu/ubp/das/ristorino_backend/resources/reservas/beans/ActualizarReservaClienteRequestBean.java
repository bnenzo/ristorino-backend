package ar.edu.ubp.das.ristorino_backend.resources.reservas.beans;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ActualizarReservaClienteRequest")
public class ActualizarReservaClienteRequestBean {

  private String fechaReserva;
  private Integer cantAdultos;
  private String horaReserva;
  private String codReservaSucursal;
  private String fechaCancelacion;
  private Integer nroRestaurante;

  public Integer getNroRestaurante() {
    return nroRestaurante;
  }

  public void setNroRestaurante(Integer nroRestaurante) {
    this.nroRestaurante = nroRestaurante;
  }

  public String getFechaCancelacion() {
    return fechaCancelacion;
  }

  public void setFechaCancelacion(String fechaCancelacion) {
    this.fechaCancelacion = fechaCancelacion;
  }

  public String getCodReservaSucursal() {
    return codReservaSucursal;
  }

  public void setCodReservaSucursal(String codReservaSucursal) {
    this.codReservaSucursal = codReservaSucursal;
  }

  public String getFechaReserva() {
    return fechaReserva;
  }

  public void setFechaReserva(String fechaReserva) {
    this.fechaReserva = fechaReserva;
  }

  public String getHoraReserva() {
    return horaReserva;
  }

  public void setHoraReserva(String horaReserva) {
    this.horaReserva = horaReserva;
  }

  public Integer getCantAdultos() {
    return cantAdultos;
  }

  public void setCantAdultos(Integer cantAdultos) {
    this.cantAdultos = cantAdultos;
  }
}
