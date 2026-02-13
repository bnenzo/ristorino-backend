package ar.edu.ubp.das.ristorino_backend.resources.localidades;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ar.edu.ubp.das.ristorino_backend.repositories.localidades.LocalidadRepository;
import ar.edu.ubp.das.ristorino_backend.repositories.localidades.beans.LocalidadBean;

@RestController
@RequestMapping("/ristorino")
public class LocalidadResource {

  @Autowired
  private LocalidadRepository localidadRepository;

  @GetMapping("/localidades")
  public List<LocalidadBean> obtenerLocalidades() {
    return localidadRepository.obtenerLocalidades();
  }
}
