package ar.edu.ubp.das.ristorino_backend.services.reservas.Dto;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CrearReservaDesdeRistorinoRequest")
public class CrearReservaConClienteDTO {

  private ClienteRestauranteDTO cliente;
  private CrearReservaRestauranteDTO reserva;

  public CrearReservaConClienteDTO() {
  }

  public ClienteRestauranteDTO getCliente() {
    return cliente;
  }

  public void setCliente(ClienteRestauranteDTO cliente) {
    this.cliente = cliente;
  }

  public CrearReservaRestauranteDTO getReserva() {
    return reserva;
  }

  public void setReserva(CrearReservaRestauranteDTO reserva) {
    this.reserva = reserva;
  }
}
