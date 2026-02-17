package ar.edu.ubp.das.ristorino_backend.repositories.costos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.das.ristorino_backend.components.SimpleJdbcCallFactory;
import ar.edu.ubp.das.ristorino_backend.repositories.costos.beans.CostoBean;

@Repository
public class CostosRepository {

  @Autowired
  private SimpleJdbcCallFactory jdbcCallFactory;

  public CostoBean obtenerCostoPorTipo(String tipoCosto) {

    SqlParameterSource params = new MapSqlParameterSource()
        .addValue("tipo_costo", tipoCosto);

    return jdbcCallFactory.executeQuery(
        "sp_obtener_costo_por_tipo",
        "dbo",
        params,
        "costos",
        CostoBean.class).get(0);
  }
}
