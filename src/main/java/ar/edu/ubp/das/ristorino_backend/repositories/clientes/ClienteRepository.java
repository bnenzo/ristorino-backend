package ar.edu.ubp.das.ristorino_backend.repositories.clientes;

import java.sql.Types;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.das.ristorino_backend.beans.ClienteBean;
import ar.edu.ubp.das.ristorino_backend.components.SimpleJdbcCallFactory;
import ar.edu.ubp.das.ristorino_backend.resources.auth.beans.ObtenerInformacionUsuarioParaLogin;
import ar.edu.ubp.das.ristorino_backend.resources.auth.beans.ObtenerInformacionUsuarioResponseBean;

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

  // OBTENER UN CLIENTE POR EMAIL
  public ObtenerInformacionUsuarioResponseBean obtenerClientePorEmail(String email) {
    SqlParameterSource params = new MapSqlParameterSource()
        .addValue("email", email);

    return jdbcCallFactory.executeQuery(
        "sp_obtener_cliente_por_email", "dbo", params, "clientes", ObtenerInformacionUsuarioResponseBean.class).get(0);
  }

  // OBTENER UN CLIENTE POR EMAIL PARA VALIDACION DE LOGIN
  public ObtenerInformacionUsuarioParaLogin obtenerClientePorEmailParaLogin(String email) {
    SqlParameterSource params = new MapSqlParameterSource()
        .addValue("email", email);

    return jdbcCallFactory.executeQuery(
        "sp_obtener_cliente_por_email_para_login", "dbo", params, "clientes",
        ObtenerInformacionUsuarioParaLogin.class).get(0);
  }

  // VALIDAR USUARIO
  public void validarUsuario(String email, String clave) {
    SqlParameterSource params = new MapSqlParameterSource()
        .addValue("correo", email)
        .addValue("clave", clave);

    jdbcCallFactory.executeWithOutputs(
        "sp_validar_login_cliente",
        "dbo",
        params);

  }

  // =================================================
  // REGISTRAR CLIENTE + PREFERENCIAS GASTRONÓMICAS
  // =================================================
  public Map<String, Object> registrarClienteConPreferencias(
      String apellido,
      String nombre,
      Integer dni,
      String clave,
      String correo,
      String telefonos,
      Integer nroLocalidad,
      String preferencias) {

    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("apellido", apellido)
        .addValue("nombre", nombre)
        .addValue("dni", dni)
        .addValue("clave", clave)
        .addValue("correo", correo)
        .addValue("telefonos", telefonos)
        .addValue("nro_localidad", nroLocalidad)
        .addValue("preferencias", preferencias);

    return jdbcCallFactory.executeWithOutputs(
        "sp_registrar_cliente",
        "dbo",
        params);
  }

}
