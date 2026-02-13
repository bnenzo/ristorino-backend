package ar.edu.ubp.das.ristorino_backend.repositories.localidades;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.das.ristorino_backend.components.SimpleJdbcCallFactory;
import ar.edu.ubp.das.ristorino_backend.repositories.localidades.beans.LocalidadBean;

@Repository
public class LocalidadRepository {

  @Autowired
  private SimpleJdbcCallFactory jdbcCallFactory;

  // =============================
  // OBTENER TODAS LAS LOCALIDADES
  // =============================
  public List<LocalidadBean> obtenerLocalidades() {

    return jdbcCallFactory.executeQuery(
        "sp_get_localidades",
        "dbo",
        "localidades",
        LocalidadBean.class);
  }
}
