package ar.edu.ubp.das.ristorino_backend.resources.clientes;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.ubp.das.ristorino_backend.beans.ClienteBean;
import ar.edu.ubp.das.ristorino_backend.repositories.clientes.ClienteRepository;
import ar.edu.ubp.das.ristorino_backend.services.clientes.ClientesService;
import ar.edu.ubp.das.ristorino_backend.services.clientes.Dto.RegistrarClienteRequestDTO;

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

  @Autowired
  private ClientesService clientesService;

  // ============================
  // REGISTRAR CLIENTE + PREFS
  // ============================
  @PostMapping("/registrar")
  public ResponseEntity<?> registrarCliente(
      @RequestBody RegistrarClienteRequestDTO request) {

    try {
      Integer nroCliente = clientesService.registrarClienteConPreferencias(request);

      return ResponseEntity
          .status(HttpStatus.CREATED)
          .body(Map.of(
              "message", "Cliente registrado correctamente",
              "nroCliente", nroCliente));

    } catch (RuntimeException e) {

      // Correo duplicado
      if (e.getMessage() != null &&
          e.getMessage().toLowerCase().contains("correo")) {

        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(Map.of(
                "message", e.getMessage()));
      }

      // Error genérico
      return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body(Map.of(
              "message", "Error al registrar cliente"));
    }
  }
}
