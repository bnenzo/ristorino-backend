package ar.edu.ubp.das.ristorino_backend.repositories.configuracion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.das.ristorino_backend.components.SimpleJdbcCallFactory;
import ar.edu.ubp.das.ristorino_backend.config.beans.ConfigBean;
import ar.edu.ubp.das.ristorino_backend.repositories.configuracion.beans.ConfiguracionRestauranteBean;

@Repository
public class ConfiguracionRepository {
  @Autowired
  private SimpleJdbcCallFactory jdbcCallFactory;

  public ConfigBean mapToConfig(List<ConfiguracionRestauranteBean> attrs) {

    if (attrs == null || attrs.isEmpty()) {
      throw new IllegalStateException("No hay configuración");
    }

    String baseUrl = null;
    String namespace = null;
    String serviceName = null;
    String portName = null;
    String backendType = null;

    for (ConfiguracionRestauranteBean a : attrs) {
      if (a == null || a.getCodAtributo() == null) {
        continue;
      }

      switch (a.getCodAtributo()) {
        case "api_base":
          baseUrl = a.getValor();
          break;

        case "namespace":
          namespace = a.getValor();
          break;

        case "service_name":
          serviceName = a.getValor();
          break;

        case "port_name":
          portName = a.getValor();
          break;

        case "backend_type":
          backendType = a.getValor();
          break;

      }
    }

    return new ConfigBean(
        baseUrl,
        namespace,
        serviceName,
        portName,
        backendType);
  }

  public ConfigBean obtenerConfiguracionRestaunte(int nroRestaurante) {

    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("nro_restaurante", nroRestaurante);

    List<ConfiguracionRestauranteBean> configuraciones = jdbcCallFactory.executeQuery(
        "sp_get_configuration_by_restaurant_id", "dbo", params,
        "configuracion_restaurantes",
        ConfiguracionRestauranteBean.class);

    return mapToConfig(configuraciones);
  }
}
