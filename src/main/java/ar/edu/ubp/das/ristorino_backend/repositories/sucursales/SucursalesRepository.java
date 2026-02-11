package ar.edu.ubp.das.ristorino_backend.repositories.sucursales;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.das.ristorino_backend.components.SimpleJdbcCallFactory;
import ar.edu.ubp.das.ristorino_backend.repositories.sucursales.beans.ZonasSucursalesBean;

@Repository
public class SucursalesRepository {
  @Autowired
  private SimpleJdbcCallFactory jdbcCallFactory;

  // GET SUCURSALES DE UN RESTAURANTE (POR NRO_RESTAURANTE)
  public List<ZonasSucursalesBean> obtenerSucursalesRestaurante(Integer nroRestaurante) {
    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("nro_restaurante", nroRestaurante);

    return jdbcCallFactory.executeQuery("sp_obtener_sucursales_por_nro_restaurante", "dbo", params,
        "sucursales_restaurantes",
        ZonasSucursalesBean.class);
  }

}
