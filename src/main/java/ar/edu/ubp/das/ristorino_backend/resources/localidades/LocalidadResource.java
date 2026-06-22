package ar.edu.ubp.das.ristorino_backend.resources.localidades;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ar.edu.ubp.das.ristorino_backend.beans.localidades.LocalidadResponseBean;
import ar.edu.ubp.das.ristorino_backend.repositories.localidades.LocalidadRepository;

@RestController
@RequestMapping("/ristorino")
public class LocalidadResource {

  @Autowired
  private LocalidadRepository localidadRepository;

  @GetMapping("/localidades")
  public List<LocalidadResponseBean> obtenerLocalidades() {
    return localidadRepository.obtenerLocalidades();
  }
}
