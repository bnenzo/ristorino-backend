package ar.edu.ubp.das.ristorino_backend.resources.preferencias;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ar.edu.ubp.das.ristorino_backend.beans.preferencias.PreferenciaResponseBean;
import ar.edu.ubp.das.ristorino_backend.repositories.preferencias.PreferenciaRepository;

@RestController
@RequestMapping("/ristorino")
public class PreferenciaResource {

  @Autowired
  private PreferenciaRepository preferenciaRepository;

  @GetMapping("/preferencias")
  public List<PreferenciaResponseBean> obtenerPreferencias() {
    return preferenciaRepository.obtenerPreferenciasGastronomicas();
  }
}
