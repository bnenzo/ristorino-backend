package ar.edu.ubp.das.ristorino_backend.repositories;

import ar.edu.ubp.das.ristorino_backend.beans.PromocionContenidoBean;
import ar.edu.ubp.das.ristorino_backend.beans.ProvinciaBean;
import ar.edu.ubp.das.ristorino_backend.beans.RegistrarClickPromocionBody;
import ar.edu.ubp.das.ristorino_backend.components.SimpleJdbcCallFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.Map;

@Repository
public class RistorinoRepository {

  @Autowired
  private SimpleJdbcCallFactory jdbcCallFactory;

  public List<ProvinciaBean> getPaises() {
    return jdbcCallFactory.executeQuery("sp_get_provincias", "dbo", "paises", ProvinciaBean.class);
  }

  public List<PromocionContenidoBean> getPromociones() {
    MapSqlParameterSource p = new MapSqlParameterSource()
        .addValue("nro_restaurante", null)
        .addValue("nro_sucursal", null)
        .addValue("solo_vigentes", 1)
        .addValue("fecha_ref", null);

    return jdbcCallFactory.executeQuery("sp_get_promociones_contenidos", "dbo", p, "contenidos_restaurantes",
        PromocionContenidoBean.class);
  }

  public Integer registrarClickContenido(RegistrarClickPromocionBody registrarClickPromocionBody) {
    SqlParameterSource params = new MapSqlParameterSource()
        .addValue("nro_restaurante", registrarClickPromocionBody.getNroRestaurante())
        .addValue("nro_idioma", registrarClickPromocionBody.getNroIdioma())
        .addValue("nro_contenido", registrarClickPromocionBody.getNroContenido())
        .addValue("nro_cliente", 1, Types.INTEGER)
        .addValue("costo_click", null, Types.DECIMAL)
        .addValue("fecha_hora_registro", null, Types.TIMESTAMP)
        .addValue("validar_vigencia", 0, Types.BIT)
        .addValue("nro_click", null, Types.INTEGER); // OUTPUT

    Map<String, Object> out = jdbcCallFactory.executeWithOutputs(
        "sp_registrar_click_contenido", "dbo", params);

    return out.get("nro_click") == null ? null : ((Number) out.get("nro_click")).intValue();
  }
}
