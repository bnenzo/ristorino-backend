package ar.edu.ubp.das.ristorino_backend.repositories.localidades;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.das.ristorino_backend.beans.localidades.LocalidadResponseBean;
import ar.edu.ubp.das.ristorino_backend.components.SimpleJdbcCallFactory;

@Repository
public class LocalidadRepository {

  @Autowired
  private SimpleJdbcCallFactory jdbcCallFactory;

  // =============================
  // OBTENER TODAS LAS LOCALIDADES
  // =============================
  public List<LocalidadResponseBean> obtenerLocalidades() {

    return jdbcCallFactory.executeQuery(
        "sp_get_localidades",
        "dbo",
        "localidades",
        LocalidadResponseBean.class);
  }
}
