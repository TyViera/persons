package pe.ty.persons.expose.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.ty.persons.expose.web.filter.RequestLoggingWebFilter;
import pe.ty.persons.expose.web.filter.ResponseLoggingWebFilter;

@Configuration
public class FilterConfiguration {

  @Bean
  public RequestLoggingWebFilter requestLoggingWebFilter() {
    return new RequestLoggingWebFilter();
  }

  @Bean
  public ResponseLoggingWebFilter responseLoggingWebFilter() {
    return new ResponseLoggingWebFilter();
  }

}
