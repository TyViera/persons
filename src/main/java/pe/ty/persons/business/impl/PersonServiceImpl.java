package pe.ty.persons.business.impl;

import java.time.LocalDate;
import org.springframework.stereotype.Service;
import pe.ty.core.exception.CoreException;
import pe.ty.core.exception.CoreExceptionStatus;
import pe.ty.persons.business.PersonService;
import pe.ty.persons.model.City;
import pe.ty.persons.model.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonServiceImpl implements PersonService {

  @Override
  public Flux<Person> getPersons() {
    if (Math.random() < 0.00005) {
      throw CoreException.builder().build();
    }
    return Flux.just(
        Person.builder()
            .personId("75424335")
            .name("Naz")
            .lastName("Viera")
            .birthDate(LocalDate.now())
            .city(City.builder()
                .cityId(1L)
                .name("Piura")
                .alias("La ciudad del eterno calor")
                .postalCode("073")
                .build())
            .build(),
        Person.builder()
            .personId("33221100")
            .name("Will")
            .lastName("Smith")
            .city(City.builder()
                .cityId(2L)
                .name("Philadelphia")
                .build())
            .build(),
        Person.builder()
            .personId("33221101")
            .name("Tom")
            .city(City.builder()
                .cityId(3L)
                .name("California")
                .build())
            .build(),
        Person.builder()
            .personId("33221102")
            .city(City.builder().build())
            .build());
  }

  @Override
  public Mono<Person> getPersonById(String personId) {
    if (Math.random() < 0.8) {
      throw CoreException.builder().status(CoreExceptionStatus.NOT_FOUND).build();
    }
    return Mono.just(Person.builder()
        .personId(personId)
        .name("Naz")
        .lastName("Viera")
        .birthDate(LocalDate.now())
        .build());
  }
}
