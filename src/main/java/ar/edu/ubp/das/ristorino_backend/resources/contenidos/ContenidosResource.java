package ar.edu.ubp.das.ristorino_backend.resources.contenidos;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ar.edu.ubp.das.ristorino_backend.beans.PromocionContenidoBean;
import ar.edu.ubp.das.ristorino_backend.components.AI.genIA.GenAI;
import ar.edu.ubp.das.ristorino_backend.components.AI.genIA.SystemPrompts;
import ar.edu.ubp.das.ristorino_backend.repositories.contenidos.ContenidosRepository;
import ar.edu.ubp.das.ristorino_backend.repositories.contenidos.beans.ObtenerContenidosSinContenidosIABean;
import ar.edu.ubp.das.ristorino_backend.repositories.idiomas.IdiomasRepository;
import ar.edu.ubp.das.ristorino_backend.repositories.idiomas.beans.IdiomasBean;

@RestController
@RequestMapping("/ristorino")
public class ContenidosResource {
  @Autowired
  private ContenidosRepository contenidosRepository;

  @Autowired
  private IdiomasRepository idiomasRepository;

  @Autowired
  private GenAI genAI;

  @GetMapping("/promociones")
  public ResponseEntity<List<PromocionContenidoBean>> obtenerPromociones() {
    List<PromocionContenidoBean> promociones = contenidosRepository.getPromociones();
    return ResponseEntity.ok(promociones);
  }

  @GetMapping("/test-promociones")
  public ResponseEntity<List<ObtenerContenidosSinContenidosIABean>> obtenerContenidosSinContenidosIA()
      throws JsonProcessingException {
    List<ObtenerContenidosSinContenidosIABean> promociones = contenidosRepository.obtenerContenidosSinContenidosIA();
    List<IdiomasBean> idiomas = idiomasRepository.obtenerIdiomas();

    ObjectMapper om = new ObjectMapper();
    om.registerModule(new JavaTimeModule());
    om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    Map<String, Object> input = new LinkedHashMap<>();
    input.put("idiomas", idiomas);
    input.put("contenidos", promociones);

    String inputString = om.writeValueAsString(input);

    String text = genAI.generate(inputString, SystemPrompts.RESTAURANTE_MULTILINGUE);

    List<ObtenerContenidosSinContenidosIABean> list = om.readValue(text,
        new TypeReference<List<ObtenerContenidosSinContenidosIABean>>() {
        });

    for (ObtenerContenidosSinContenidosIABean item : list) {
      contenidosRepository.insertarContenidoGeneradoConIA(item);
    }

    return ResponseEntity.ok(list);
  }

}
