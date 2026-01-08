package ar.edu.ubp.das.ristorino_backend.repositories;

import ar.edu.ubp.das.ristorino_backend.components.SimpleJdbcCallFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

@Repository
public class RistorinoRepository {

	@Autowired
	private SimpleJdbcCallFactory jdbcCallFactory;

}
