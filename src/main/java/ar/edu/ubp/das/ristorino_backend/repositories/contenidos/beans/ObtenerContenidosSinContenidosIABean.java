package ar.edu.ubp.das.ristorino_backend.repositories.contenidos.beans;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ObtenerContenidosSinContenidosIABean {
  private Integer nroRestaurante;
  private Integer nroIdioma;
  private Integer nroContenido;
  private Integer nroSucursal;

  private String contenidoPromocional;

  public String getContenidoPromocional() {
    return contenidoPromocional;
  }

  public void setContenidoPromocional(String contenidoPromocional) {
    this.contenidoPromocional = contenidoPromocional;
  }

  private String imagenPromocional;

  public String getImagenPromocional() {
    return imagenPromocional;
  }

  public void setImagenPromocional(String imagenPromocional) {
    this.imagenPromocional = imagenPromocional;
  }

  private String contenidoAPublicar;
  private LocalDate fechaIniVigencia;

  public LocalDate getFechaIniVigencia() {
    return fechaIniVigencia;
  }

  public void setFechaIniVigencia(LocalDate fechaIniVigencia) {
    this.fechaIniVigencia = fechaIniVigencia;
  }

  private LocalDate fechaFinVigencia;

  public LocalDate getFechaFinVigencia() {
    return fechaFinVigencia;
  }

  public void setFechaFinVigencia(LocalDate fechaFinVigencia) {
    this.fechaFinVigencia = fechaFinVigencia;
  }

  private BigDecimal costoClick;

  public BigDecimal getCostoClick() {
    return costoClick;
  }

  public void setCostoClick(BigDecimal costoClick) {
    this.costoClick = costoClick;
  }

  private String codContenidoRestaurante;

  public String getCodContenidoRestaurante() {
    return codContenidoRestaurante;
  }

  public void setCodContenidoRestaurante(String codContenidoRestaurante) {
    this.codContenidoRestaurante = codContenidoRestaurante;
  }

  public ObtenerContenidosSinContenidosIABean() {
  }

  public Integer getNroRestaurante() {
    return nroRestaurante;
  }

  public void setNroRestaurante(Integer nroRestaurante) {
    this.nroRestaurante = nroRestaurante;
  }

  public Integer getNroIdioma() {
    return nroIdioma;
  }

  public void setNroIdioma(Integer nroIdioma) {
    this.nroIdioma = nroIdioma;
  }

  public Integer getNroContenido() {
    return nroContenido;
  }

  public void setNroContenido(Integer nroContenido) {
    this.nroContenido = nroContenido;
  }

  public Integer getNroSucursal() {
    return nroSucursal;
  }

  public void setNroSucursal(Integer nroSucursal) {
    this.nroSucursal = nroSucursal;
  }

  public String getContenidoAPublicar() {
    return contenidoAPublicar;
  }

  public void setContenidoAPublicar(String contenidoAPublicar) {
    this.contenidoAPublicar = contenidoAPublicar;
  }
}