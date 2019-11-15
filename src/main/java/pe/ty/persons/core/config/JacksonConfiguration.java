package pe.ty.persons.core.config;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@AllArgsConstructor
public class JacksonConfiguration {

  private ObjectMapper mapper;

  @PostConstruct
  public void postCreation() {
    configureObjectMapper();
  }

  private void configureObjectMapper() {
    log.info("Configuring ObjectMapper...");
    mapper.setSerializationInclusion(Include.NON_NULL);
  }

}