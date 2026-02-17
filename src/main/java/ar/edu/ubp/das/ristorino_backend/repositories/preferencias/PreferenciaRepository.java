package ar.edu.ubp.das.ristorino_backend.repositories.preferencias;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.das.ristorino_backend.components.SimpleJdbcCallFactory;
import ar.edu.ubp.das.ristorino_backend.repositories.preferencias.beans.ObtenerPreferenciasSucursalRestauranteBean;
import ar.edu.ubp.das.ristorino_backend.repositories.preferencias.beans.PreferenciaBean;

@Repository
public class PreferenciaRepository {

  @Autowired
  private SimpleJdbcCallFactory jdbcCallFactory;

  // ==================================
  // OBTENER PREFERENCIAS GASTRONÓMICAS
  // ==================================
  public List<PreferenciaBean> obtenerPreferenciasGastronomicas() {

    return jdbcCallFactory.executeQuery(
        "sp_get_preferencias_gastronomicas",
        "dbo",
        "preferencias",
        PreferenciaBean.class);
  }

  public List<ObtenerPreferenciasSucursalRestauranteBean> obtenerPreferenciasSucursalRestaurante() {
    MapSqlParameterSource p = new MapSqlParameterSource()
        .addValue("nro_idioma", 1);

    return jdbcCallFactory.executeQuery(
        "sp_get_restaurantes_tags_flat",
        "dbo",
        p,
        "preferencias_restaurantes",
        ObtenerPreferenciasSucursalRestauranteBean.class);
  }
}
