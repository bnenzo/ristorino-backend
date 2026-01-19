package ar.edu.ubp.das.ristorino_backend.repositories.contenidos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.das.ristorino_backend.beans.PromocionContenidoBean;
import ar.edu.ubp.das.ristorino_backend.components.SimpleJdbcCallFactory;
import ar.edu.ubp.das.ristorino_backend.repositories.contenidos.beans.ObtenerContenidosSinContenidosIABean;

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

}
