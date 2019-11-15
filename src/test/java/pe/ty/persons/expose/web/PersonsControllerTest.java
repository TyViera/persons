package pe.ty.persons.expose.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(controllers = PersonsController.class)
@ExtendWith(SpringExtension.class)
class PersonsControllerTest {

  @Value("${application.rest.base-url}/" + PersonsController.RESOURCE_NAME)
  private String personsUri;

  @Autowired
  private WebTestClient webClient;

  @BeforeEach
  void setUp() {
  }

  @Test
  void getPersons_thenReturnOk() throws Exception {
    this.webClient.get().uri(personsUri)
        .exchange()
        .expectStatus().isOk()
        .expectBody().jsonPath("$[0].personId").isEqualTo("75424335");
  }

}