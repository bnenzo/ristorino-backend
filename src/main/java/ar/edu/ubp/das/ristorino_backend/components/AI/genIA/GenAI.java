package ar.edu.ubp.das.ristorino_backend.components.AI.genIA;

import com.google.genai.Client;
import com.google.genai.types.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GenAI {

  private final Client genaiClient;
  private static final String DEFAULT_MODEL = "gemini-2.5-flash-lite";

  public GenAI(@Value("${api.key}") String apiKey) {
    this.genaiClient = Client.builder()
        .apiKey(apiKey)
        .build();
  }

  public String generate(String inputString, String systemInstruction) {
    GenerateContentConfig cfg = GenerateContentConfig.builder()
        .systemInstruction(Content.fromParts(Part.fromText(systemInstruction)))
        .candidateCount(1)
        .maxOutputTokens(2000)
        .build();

    GenerateContentResponse response = genaiClient.models.generateContent(DEFAULT_MODEL, inputString, cfg);

    String text = response.text();
    if (text == null || text.isBlank()) {
      throw new IllegalStateException("Sin contenido de GenAI (coords)");
    }
    return text;
  }

  public Client getClient() {
    return genaiClient;
  }
}
