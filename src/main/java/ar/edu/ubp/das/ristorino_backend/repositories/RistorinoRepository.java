package ar.edu.ubp.das.ristorino_backend.repositories;

import ar.edu.ubp.das.ristorino_backend.beans.ClicksContenidosRestaurantesBean;
import ar.edu.ubp.das.ristorino_backend.beans.DatosRestauranteBean;
import ar.edu.ubp.das.ristorino_backend.beans.PreferenciaRestauranteBean;
import ar.edu.ubp.das.ristorino_backend.beans.PromocionContenidoBean;
import ar.edu.ubp.das.ristorino_backend.beans.RegistrarClickPromocionBody;
import ar.edu.ubp.das.ristorino_backend.beans.SucursalRestauranteBean;
import ar.edu.ubp.das.ristorino_backend.components.Httpful;
import ar.edu.ubp.das.ristorino_backend.components.SimpleJdbcCallFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.google.gson.JsonObject;

import java.sql.Types;
import java.util.List;

@Repository
public class RistorinoRepository {

  @Autowired
  private SimpleJdbcCallFactory jdbcCallFactory;

  public List<PromocionContenidoBean> getPromociones() {
    MapSqlParameterSource p = new MapSqlParameterSource()
        .addValue("nro_restaurante", null)
        .addValue("nro_sucursal", null)
        .addValue("solo_vigentes", 1)
        .addValue("fecha_ref", null);

    return jdbcCallFactory.executeQuery("sp_get_promociones_contenidos", "dbo", p, "contenidos_restaurantes",
        PromocionContenidoBean.class);
  }

  public void registrarClickContenido(RegistrarClickPromocionBody registrarClickPromocionBody) {
    SqlParameterSource params = new MapSqlParameterSource()
        .addValue("nro_restaurante", registrarClickPromocionBody.getNroRestaurante())
        .addValue("nro_idioma", registrarClickPromocionBody.getNroIdioma())
        .addValue("nro_contenido", registrarClickPromocionBody.getNroContenido())
        .addValue("nro_cliente", 1, Types.INTEGER)
        .addValue("costo_click", null, Types.DECIMAL)
        .addValue("fecha_hora_registro", null, Types.TIMESTAMP)
        .addValue("validar_vigencia", 0, Types.BIT)
        .addValue("nro_click", null, Types.INTEGER); // OUTPUT

    jdbcCallFactory.executeWithOutputs(
        "sp_registrar_click_contenido", "dbo", params);
  }

  public void notificarClicksPromocionManual() {

    // Se obtienen los clicks en las promociones no notificados
    List<ClicksContenidosRestaurantesBean> clicksSinNotificar = jdbcCallFactory.executeQuery(
        "sp_get_clicks_no_notificados", "dbo", "clicks_contenidos_restaurantes",
        ClicksContenidosRestaurantesBean.class);

    // Se recorren los clicks para enviar 1 a 1 al backend del restaurante
    for (ClicksContenidosRestaurantesBean clickSinNotificar : clicksSinNotificar) {
      String base = "http://localhost:8086/api/v1";

      JsonObject body = new JsonObject();
      body.addProperty("nroRestaurante", clickSinNotificar.getNroRestaurante());
      body.addProperty("nroContenido", clickSinNotificar.getNroContenido());
      body.addProperty("nroClick", clickSinNotificar.getNroClick());
      body.addProperty("fechaHoraRegistro", clickSinNotificar.getFechaHoraRegistro());
      body.addProperty("nroCliente", clickSinNotificar.getNroCliente());
      body.addProperty("costoClick", clickSinNotificar.getCostoClick());

      Httpful http = new Httpful(base)
          .path("/la-bella-pizza/registrar_click_contenido")
          .post(body);

      http.execute(String.class);

      // Una vez se inserto el click en la DB del restaurante actualizamos nuestra DB
      SqlParameterSource params = new MapSqlParameterSource()
          .addValue("nro_restaurante", clickSinNotificar.getNroRestaurante())
          .addValue("nro_idioma", clickSinNotificar.getNroIdioma())
          .addValue("nro_contenido", clickSinNotificar.getNroContenido())
          .addValue("nro_click", clickSinNotificar.getNroClick())
          .addValue("notificado", 1, Types.INTEGER);

      jdbcCallFactory.executeWithOutputs(
          "sp_set_click_notificado", "dbo", params);

    }
  }

  // GET DATOS RESTAURANTE
  public DatosRestauranteBean getDatosRestaurante(Integer nroRestaurante) {
    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("nro_restaurante", nroRestaurante);

    List<DatosRestauranteBean> result = jdbcCallFactory.executeQuery(
        "sp_get_datos_restaurante",
        "dbo",
        params,
        "restaurantes",
        DatosRestauranteBean.class);

    return result.isEmpty() ? null : result.get(0);
  }

  // GET SUCURSALES DE UN RESTAURANTE
  public List<SucursalRestauranteBean> getSucursalesRestaurante(Integer nroRestaurante) {
    SqlParameterSource params = new MapSqlParameterSource()
        .addValue("nro_restaurante", nroRestaurante);

    return jdbcCallFactory.executeQuery(
        "sp_get_sucursales_restaurante",
        "dbo",
        params,
        "sucursales_restaurantes",
        SucursalRestauranteBean.class);
  }

  // GET PREFERENCIAS DE RESTAURANTE
  public List<PreferenciaRestauranteBean> getPreferenciasRestaurante(Integer nroRestaurante) {
    SqlParameterSource params = new MapSqlParameterSource()
        .addValue("nro_restaurante", nroRestaurante);

    return jdbcCallFactory.executeQuery(
        "sp_get_preferencias_restaurante",
        "dbo",
        params,
        "preferencias_restaurantes",
        PreferenciaRestauranteBean.class);
  }

}
