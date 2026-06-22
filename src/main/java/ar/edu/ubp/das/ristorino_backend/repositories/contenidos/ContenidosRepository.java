package ar.edu.ubp.das.ristorino_backend.repositories.contenidos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.ubp.das.ristorino_backend.beans.ContenidoNoPublicadoBean;
import ar.edu.ubp.das.ristorino_backend.beans.PromocionContenidoBean;
import ar.edu.ubp.das.ristorino_backend.beans.RestauranteIdBean;
import ar.edu.ubp.das.ristorino_backend.beans.contenidos.BuscadoPromocionesIAOutput;
import ar.edu.ubp.das.ristorino_backend.beans.contenidos.BuscarContenidosIAResponseBean;
import ar.edu.ubp.das.ristorino_backend.beans.contenidos.ObtenerContenidosResponseBean;
import ar.edu.ubp.das.ristorino_backend.components.SimpleJdbcCallFactory;
import ar.edu.ubp.das.ristorino_backend.repositories.contenidos.beans.ObtenerContenidosSinContenidosIABean;

@Repository
public class ContenidosRepository {
  @Autowired
  private SimpleJdbcCallFactory jdbcCallFactory;

  public List<ObtenerContenidosResponseBean> getPromociones(Integer nroRestaurante, Integer nroIdioma) {
    MapSqlParameterSource p = new MapSqlParameterSource()
        .addValue("nro_restaurante", nroRestaurante)
        .addValue("nro_sucursal", null)
        .addValue("solo_vigentes", 1)
        .addValue("nro_idioma", nroIdioma)
        .addValue("fecha_ref", null);

    return jdbcCallFactory.executeQuery("sp_get_promociones_contenidos", "dbo", p, "contenidos_restaurantes",
        ObtenerContenidosResponseBean.class);
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
        .addValue("contenido_promocional", null)
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

  public List<ObtenerContenidosSinContenidosIABean> obtenerContenidosSinContenidosIA() {
    return jdbcCallFactory.executeQuery("sp_get_contenidos_sin_contenido_IA", "dbo", "contenidos_restaurantes",
        ObtenerContenidosSinContenidosIABean.class);
  }

  public void insertarContenidoGeneradoConIA(ObtenerContenidosSinContenidosIABean contenidoGenerado) {
    MapSqlParameterSource p = new MapSqlParameterSource()
        .addValue("nro_restaurante", contenidoGenerado.getNroRestaurante())
        .addValue("nro_idioma", contenidoGenerado.getNroIdioma())
        .addValue("nro_contenido", contenidoGenerado.getNroContenido())
        .addValue("nro_sucursal", contenidoGenerado.getNroSucursal())
        .addValue("contenido_promocional", contenidoGenerado.getContenidoPromocional())
        .addValue("imagen_promocional", contenidoGenerado.getImagenPromocional())
        .addValue("contenido_a_publicar", contenidoGenerado.getContenidoAPublicar())
        .addValue("fecha_ini_vigencia", contenidoGenerado.getFechaIniVigencia())
        .addValue("fecha_fin_vigencia", contenidoGenerado.getFechaFinVigencia())
        .addValue("costo_click", contenidoGenerado.getCostoClick())
        .addValue("cod_contenido_restaurante", contenidoGenerado.getCodContenidoRestaurante());

    jdbcCallFactory.executeWithOutputs("sp_insertar_o_actualizar_contenido_generado_con_ia", "dbo", p);
  }

  public List<BuscarContenidosIAResponseBean> obtenerContenidosPorListaDeContenidosIds(
      List<BuscadoPromocionesIAOutput> lista)
      throws JsonProcessingException {
    ObjectMapper om = new ObjectMapper();
    String json = om.writeValueAsString(lista);

    MapSqlParameterSource p = new MapSqlParameterSource();
    p.addValue("json", json);
    p.addValue("solo_vigentes", null);
    p.addValue("fecha_ref", null);

    return jdbcCallFactory.executeQuery(
        "sp_get_promociones_por_lista",
        "dbo",
        p,
        "contenidos_restaurante",
        BuscarContenidosIAResponseBean.class);

  }

}
