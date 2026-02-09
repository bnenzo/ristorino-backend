package ar.edu.ubp.das.ristorino_backend.resources.auth;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import ar.edu.ubp.das.ristorino_backend.repositories.clientes.ClienteRepository;
import ar.edu.ubp.das.ristorino_backend.resources.auth.beans.LoginRequestBean;
import ar.edu.ubp.das.ristorino_backend.resources.auth.beans.LoginResponseBean;
import ar.edu.ubp.das.ristorino_backend.resources.auth.beans.ObtenerInformacionUsuarioResponseBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/ristorino")
public class AuthResource {

  @Autowired
  private ClienteRepository clienteRepository;

  @Value("${security.jwt.secret}")
  private String jwtSecret;

  @PostMapping("/auth/login")
  public ResponseEntity<LoginResponseBean> login(@RequestBody LoginRequestBean request) throws Exception {

    // 🔐 VALIDACIÓN DE USUARIO (ejemplo simple)
    if (!"renzo.letona@example.com".equals(request.getEmail()) ||
        !"1234".equals(request.getClave())) {
      return ResponseEntity.status(401).build();
    }

    // 🕒 Fechas
    Instant now = Instant.now();
    Instant exp = now.plusSeconds(3600); // 1 hora

    // 📦 Claims
    JWTClaimsSet claims = new JWTClaimsSet.Builder()
        .subject(request.getEmail())
        .issueTime(Date.from(now))
        .expirationTime(Date.from(exp))
        .build();

    // 🔑 Firma HS256
    SecretKeySpec key = new SecretKeySpec(
        jwtSecret.getBytes(StandardCharsets.UTF_8),
        "HmacSHA256");

    SignedJWT signedJWT = new SignedJWT(
        new JWSHeader(JWSAlgorithm.HS256),
        claims);

    signedJWT.sign(new MACSigner(key));

    String token = signedJWT.serialize();

    return ResponseEntity.ok(new LoginResponseBean(token));
  }

  // OBTENER UN CLIENTE POR EMAIL
  @GetMapping("/auth/me")
  public ObtenerInformacionUsuarioResponseBean obtenerInformacionUsuario(@AuthenticationPrincipal Jwt jwt) {
    String email = jwt.getSubject();
    return clienteRepository.obtenerClientePorEmail(email);
  }

}
