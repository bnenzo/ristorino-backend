package ar.edu.ubp.das.ristorino_backend.repositories.contenidos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.das.ristorino_backend.beans.ContenidoNoPublicadoBean;
import ar.edu.ubp.das.ristorino_backend.beans.PromocionContenidoBean;
import ar.edu.ubp.das.ristorino_backend.beans.RestauranteIdBean;
import ar.edu.ubp.das.ristorino_backend.components.SimpleJdbcCallFactory;

@Repository
public class ContenidosRepository {
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

  // POSIBLEMENTE MOVER A OTRO REPOSITORY?
  public List<RestauranteIdBean> getRestaurantesId() {
    MapSqlParameterSource p = new MapSqlParameterSource();

    return jdbcCallFactory.executeQuery(
        "sp_get_restaurantes_id",
        "dbo",
        p,
        "restaurantes",
        RestauranteIdBean.class);
  }

  // INSERTAR CONTENIDOS NO PUBLICADOS OBTENIDOS DE LOS RESTAURANTES
  public void insertarContenidoNoPublicado(
      ContenidoNoPublicadoBean c,
      String codContenidoRestaurante) {

    MapSqlParameterSource p = new MapSqlParameterSource()
        .addValue("nro_restaurante", c.getNroRestaurante())
        .addValue("nro_idioma", 1)
        .addValue("nro_contenido", c.getNroContenido())
        .addValue("nro_sucursal", c.getNroSucursal())
        .addValue("contenido_promocional", "esta es una IA promo")
        .addValue("imagen_promocional", c.getImagenAPublicar())
        .addValue("contenido_a_publicar", c.getContenidoAPublicar())
        .addValue("fecha_ini_vigencia", LocalDate.now())
        .addValue("fecha_fin_vigencia", LocalDate.now().plusMonths(1))
        .addValue("costo_click", c.getCostoClick())
        .addValue("cod_contenido_restaurante", codContenidoRestaurante);

    jdbcCallFactory.executeWithOutputs(
        "sp_insert_contenido_restaurante",
        "dbo",
        p);
  }
}
