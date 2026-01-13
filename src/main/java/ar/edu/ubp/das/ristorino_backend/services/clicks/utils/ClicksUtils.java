package ar.edu.ubp.das.ristorino_backend.services.clicks.utils;

public class ClicksUtils {
  public static int obtenerNroRestaurante(String valor) {
    // Ejemplo: "LBP-1-1"
    String[] partes = valor.split("-");
    return Integer.parseInt(partes[1]);
  }

}
