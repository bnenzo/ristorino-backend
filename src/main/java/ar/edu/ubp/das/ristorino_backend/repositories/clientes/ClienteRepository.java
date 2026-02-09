package ar.edu.ubp.das.ristorino_backend.repositories.clientes;

import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.das.ristorino_backend.beans.ClienteBean;
import ar.edu.ubp.das.ristorino_backend.components.SimpleJdbcCallFactory;

@Repository
public class ClienteRepository {

  @Autowired
  private SimpleJdbcCallFactory jdbcCallFactory;

  // ==========================
  // INSERTAR UN NUEVO CLIENTE
  // ==========================
  public void insertarCliente(ClienteBean cliente) {

    SqlParameterSource params = new MapSqlParameterSource()
        .addValue("nro_cliente", cliente.getNroCliente(), Types.INTEGER)
        .addValue("apellido", cliente.getApellido())
        .addValue("nombre", cliente.getNombre())
        .addValue("clave", cliente.getClave())
        .addValue("correo", cliente.getCorreo())
        .addValue("telefonos", cliente.getTelefonos())
        .addValue("nro_localidad", cliente.getNroLocalidad(), Types.INTEGER);

    jdbcCallFactory.executeWithOutputs(
        "sp_insert_cliente", "dbo", params);
  }

  // =======================
  // OBTENER CLIENTE POR ID
  // =======================
  public ClienteBean obtenerClientePorId(Integer nroCliente) {

    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("nro_cliente", nroCliente);

    var clientes = jdbcCallFactory.executeQuery(
        "sp_get_cliente_por_id",
        "dbo",
        params,
        "clientes",
        ClienteBean.class);

    if (clientes == null || clientes.isEmpty()) {
      return null;
    }

    return clientes.get(0);
  }

}
