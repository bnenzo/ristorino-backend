package ar.edu.ubp.das.ristorino_backend.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {

  private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  private PasswordUtils() {
  }

  public static String hash(String password) {
    return encoder.encode(password);
  }

  public static boolean matches(String rawPassword, String hashedPassword) {
    return encoder.matches(rawPassword, hashedPassword);
  }
}
