package ar.edu.ubp.das.ristorino_backend.resources;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.das.ristorino_backend.repositories.RistorinoRepository;

@RestController
@RequestMapping("/ristorino")
public class RistorinoResource {

  @Autowired
  private RistorinoRepository ristorinoRepository;

}
