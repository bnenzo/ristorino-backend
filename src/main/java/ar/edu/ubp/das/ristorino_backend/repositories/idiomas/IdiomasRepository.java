package ar.edu.ubp.das.ristorino_backend.repositories.idiomas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.das.ristorino_backend.components.SimpleJdbcCallFactory;
import ar.edu.ubp.das.ristorino_backend.repositories.idiomas.beans.IdiomasBean;

@Repository
public class IdiomasRepository {
  @Autowired
  private SimpleJdbcCallFactory jdbcCallFactory;

  public List<IdiomasBean> obtenerIdiomas() {
    return jdbcCallFactory.executeQuery("sp_get_idiomas", "dbo", "idiomas",
        IdiomasBean.class);
  }
}
