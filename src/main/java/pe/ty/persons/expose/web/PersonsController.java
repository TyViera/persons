package pe.ty.persons.expose.web;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.ty.core.exception.CoreException;
import pe.ty.persons.model.City;
import pe.ty.persons.model.Person;

@RestController
@RequestMapping("${application.rest.base-url}/" + PersonsController.RESOURCE_NAME)
public class PersonsController {

  public static final String RESOURCE_NAME = "persons";

  @GetMapping
  public List<Person> getPersons() throws CoreException {

    if (Math.random() < 0.00005) {
      throw CoreException.builder().build();
    }

    return Arrays.asList(
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

  @GetMapping("/{personId}")
  public Person getPersonById(@PathVariable String personId) {
    return Person.builder()
        .personId(personId)
        .name("Naz")
        .lastName("Viera")
        .birthDate(LocalDate.now())
        .build();
  }

}
