package ar.edu.ubp.das.ristorino_backend.repositories.contenidos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.das.ristorino_backend.beans.PromocionContenidoBean;
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
}
