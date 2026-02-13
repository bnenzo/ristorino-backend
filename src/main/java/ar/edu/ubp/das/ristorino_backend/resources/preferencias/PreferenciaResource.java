package ar.edu.ubp.das.ristorino_backend.resources.preferencias;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ar.edu.ubp.das.ristorino_backend.repositories.preferencias.PreferenciaRepository;
import ar.edu.ubp.das.ristorino_backend.repositories.preferencias.beans.PreferenciaBean;

@RestController
@RequestMapping("/ristorino")
public class PreferenciaResource {

  @Autowired
  private PreferenciaRepository preferenciaRepository;

  @GetMapping("/preferencias")
  public List<PreferenciaBean> obtenerPreferencias() {
    return preferenciaRepository.obtenerPreferenciasGastronomicas();
  }
}
