package ar.edu.ubp.das.ristorino_backend.utils;

import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class Utils {

  public static String generarToken(String secret) {
    return Jwts.builder()
        .subject("ristorino")
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + 3600000))
        .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
        .compact();
  }
}
