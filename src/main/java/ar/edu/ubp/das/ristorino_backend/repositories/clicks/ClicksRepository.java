package ar.edu.ubp.das.ristorino_backend.repositories.clicks;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.das.ristorino_backend.beans.ClicksContenidosRestaurantesBean;
import ar.edu.ubp.das.ristorino_backend.beans.RegistrarClickPromocionBody;
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
        .addValue("costo_click", registrarClickPromocionBody.getCostoClick(), Types.DECIMAL)
        .addValue("cod_contenido_restaurante", registrarClickPromocionBody.getCodContenidoRestaurante());

    jdbcCallFactory.executeWithOutputs(
        "sp_registrar_click_contenido", "dbo", params);
  }

  public List<ClicksContenidosRestaurantesBean> obtenerClicksContenidosSinNotificar() {
    List<ClicksContenidosRestaurantesBean> clicksSinNotificar = jdbcCallFactory.executeQuery(
        "sp_get_clicks_no_notificados", "dbo", "clicks_contenidos_restaurantes",
        ClicksContenidosRestaurantesBean.class);
    return clicksSinNotificar;
  }

  public void actualizarClickSinNotificarANotificado(ClicksContenidosRestaurantesBean clickANotificar) {
    SqlParameterSource params = new MapSqlParameterSource()
        .addValue("nro_restaurante", clickANotificar.getNroRestaurante())
        .addValue("nro_idioma", clickANotificar.getNroIdioma())
        .addValue("nro_contenido", clickANotificar.getNroContenido())
        .addValue("nro_click", clickANotificar.getNroClick())
        .addValue("notificado", 1, Types.INTEGER);

    jdbcCallFactory.executeWithOutputs(
        "sp_set_click_notificado", "dbo", params);
  }
}
