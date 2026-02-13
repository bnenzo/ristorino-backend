package ar.edu.ubp.das.ristorino_backend.resources.idiomas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.das.ristorino_backend.repositories.idiomas.IdiomasRepository;
import ar.edu.ubp.das.ristorino_backend.repositories.idiomas.beans.IdiomasBean;

@RestController
@RequestMapping("/ristorino")
public class IdiomasResource {
  @Autowired
  private IdiomasRepository idiomasRepository;

  // OBTENER TODOS LOS IDIOMAS
  @GetMapping("/idiomas")
  public List<IdiomasBean> obtenerReservasCliente() {
    return idiomasRepository.obtenerIdiomas();
  }
}
