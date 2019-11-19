package pe.ty.persons.expose.web;

import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import pe.ty.persons.business.PersonService;
import pe.ty.persons.model.Person;
import pe.ty.persons.test.util.PersonsFakeDataUtil;
import pe.ty.test.util.FakeDataUtil;
import pe.ty.test.webflux.autoconfigure.WebFluxConfigurationTest;
import reactor.core.publisher.Flux;

@Slf4j
@WebFluxConfigurationTest
@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = PersonsController.class)
class PersonsControllerTest {

  @Value("${application.rest.base-url}/" + PersonsController.RESOURCE_NAME)
  private String personsUri;

  @Autowired
  private WebTestClient webClient;

  @MockBean
  private PersonService personService;

  @Test
  void getPersons_thenReturnOk() {
    int personsSize = FakeDataUtil.faker.number().randomDigitNotZero();
    Mockito.when(personService.getPersons()).thenReturn(getPersons(personsSize));

    this.webClient.get().uri(personsUri)
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$[0].personId").exists()
        .jsonPath("$.length()").isEqualTo(personsSize);
  }

  @Test
  void getPersons_withoutData_thenReturnNotFound() {
    Mockito.when(personService.getPersons()).thenReturn(Flux.empty());

    this.webClient.get().uri(personsUri)
        .exchange()
        .expectStatus().isNotFound()
        .expectBody()
        .jsonPath("$.code").exists()
        .jsonPath("$.message").exists()
        .jsonPath("$.component").exists()
        .jsonPath("$.errorType").exists();
  }

  private Flux<Person> getPersons(int size) {
    return Flux.fromStream(
        IntStream.range(0, size)
            .mapToObj(index -> PersonsFakeDataUtil
                .getFakePerson(false, false))
    );
  }


}