package ar.edu.ubp.das.ristorino_backend.beans;

import java.math.BigDecimal;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ClicksContenidosRestaurantes")
public class ClicksContenidosRestaurantesBean {

  private int nroRestaurante;
  private int nroIdioma;
  private int nroContenido;
  private int nroClick;
  private String fechaHoraRegistro;
  private Integer nroCliente;
  private BigDecimal costoClick;
  private int notificado;

  public int getNroRestaurante() {
    return nroRestaurante;
  }

  public void setNroRestaurante(int nroRestaurante) {
    this.nroRestaurante = nroRestaurante;
  }

  public int getNroIdioma() {
    return nroIdioma;
  }

  public void setNroIdioma(int nroIdioma) {
    this.nroIdioma = nroIdioma;
  }

  public int getNroContenido() {
    return nroContenido;
  }

  public void setNroContenido(int nroContenido) {
    this.nroContenido = nroContenido;
  }

  public int getNroClick() {
    return nroClick;
  }

  public void setNroClick(int nroClick) {
    this.nroClick = nroClick;
  }

  public String getFechaHoraRegistro() {
    return fechaHoraRegistro;
  }

  public void setFechaHoraRegistro(String fechaHoraRegistro) {
    this.fechaHoraRegistro = fechaHoraRegistro;
  }

  public Integer getNroCliente() {
    return nroCliente;
  }

  public void setNroCliente(Integer nroCliente) {
    this.nroCliente = nroCliente;
  }

  public BigDecimal getCostoClick() {
    return costoClick;
  }

  public void setCostoClick(BigDecimal costoClick) {
    this.costoClick = costoClick;
  }

  public int isNotificado() {
    return notificado;
  }

  public void setNotificado(int notificado) {
    this.notificado = notificado;
  }

  @Override
  public String toString() {
    return "ClickContenidoRestaurante{" +
        "nroRestaurante=" + nroRestaurante +
        ", nroIdioma=" + nroIdioma +
        ", nroContenido=" + nroContenido +
        ", nroClick=" + nroClick +
        ", fechaHoraRegistro=" + fechaHoraRegistro +
        ", nroCliente=" + nroCliente +
        ", costoClick=" + costoClick +
        ", notificado=" + notificado +
        '}';
  }
}
