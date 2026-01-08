package ar.edu.ubp.das.ristorino_backend.repositories.restaurantes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.das.ristorino_backend.beans.DatosRestauranteBean;
import ar.edu.ubp.das.ristorino_backend.beans.PreferenciaRestauranteBean;
import ar.edu.ubp.das.ristorino_backend.beans.SucursalRestauranteBean;
import ar.edu.ubp.das.ristorino_backend.components.SimpleJdbcCallFactory;

@Repository
public class RestaurantesRepository {
  @Autowired
  private SimpleJdbcCallFactory jdbcCallFactory;

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
