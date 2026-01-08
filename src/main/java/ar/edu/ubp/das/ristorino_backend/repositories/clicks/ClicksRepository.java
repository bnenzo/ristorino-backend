package ar.edu.ubp.das.ristorino_backend.repositories.clicks;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.google.gson.JsonObject;

import ar.edu.ubp.das.ristorino_backend.beans.ClicksContenidosRestaurantesBean;
import ar.edu.ubp.das.ristorino_backend.beans.GetApiBaseUrlBean;
import ar.edu.ubp.das.ristorino_backend.beans.RegistrarClickPromocionBody;
import ar.edu.ubp.das.ristorino_backend.components.Httpful;
import ar.edu.ubp.das.ristorino_backend.components.SimpleJdbcCallFactory;

@Repository
public class ClicksRepository {

  @Autowired
  private SimpleJdbcCallFactory jdbcCallFactory;

  public void registrarClickContenido(RegistrarClickPromocionBody registrarClickPromocionBody) {
    SqlParameterSource params = new MapSqlParameterSource()
        .addValue("nro_restaurante", registrarClickPromocionBody.getNroRestaurante())
        .addValue("nro_idioma", registrarClickPromocionBody.getNroIdioma())
        .addValue("nro_contenido", registrarClickPromocionBody.getNroContenido())
        .addValue("nro_cliente", 1, Types.INTEGER)
        .addValue("costo_click", registrarClickPromocionBody.getCostoClick(), Types.DECIMAL);

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

      // Se obtiene la api base para cada uno de esos restaurantes
      SqlParameterSource getApiBaseUrlParams = new MapSqlParameterSource()
          .addValue("nro_restaurante", clickSinNotificar.getNroRestaurante());

      List<GetApiBaseUrlBean> apiBaseUrl = jdbcCallFactory.executeQuery(
          "sp_get_apibase_by_restaurant_id",
          "dbo",
          getApiBaseUrlParams,
          "configuracion_restaurantes",
          GetApiBaseUrlBean.class);

      if (apiBaseUrl.size() != 1) {
        throw new Error("No se encontro la apibase para el restaurante");
      }

      String base = apiBaseUrl.get(0).getValor();

      JsonObject body = new JsonObject();
      body.addProperty("nroRestaurante", clickSinNotificar.getNroRestaurante());
      body.addProperty("nroContenido", clickSinNotificar.getNroContenido());
      body.addProperty("nroClick", clickSinNotificar.getNroClick());
      body.addProperty("fechaHoraRegistro", clickSinNotificar.getFechaHoraRegistro());
      body.addProperty("nroCliente", clickSinNotificar.getNroCliente());
      body.addProperty("costoClick", clickSinNotificar.getCostoClick());

      Httpful http = new Httpful(base)
          .path("/registrar_click_contenido")
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
}
