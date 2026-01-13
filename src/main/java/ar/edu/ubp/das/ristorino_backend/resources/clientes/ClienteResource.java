package ar.edu.ubp.das.ristorino_backend.resources.clientes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.ubp.das.ristorino_backend.beans.ClienteBean;
import ar.edu.ubp.das.ristorino_backend.repositories.clientes.ClienteRepository;

@RestController
@RequestMapping("/ristorino")
public class ClienteResource {

  @Autowired
  private ClienteRepository clienteRepository;

  @PostMapping("/clientes")
  public ResponseEntity<Void> registrarCliente(@RequestBody ClienteBean cliente) {

    clienteRepository.insertarCliente(cliente);

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
