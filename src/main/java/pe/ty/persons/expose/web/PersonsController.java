package pe.ty.persons.expose.web;

import java.time.LocalDate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.ty.persons.model.Person;

@RestController
@RequestMapping("/persons")
public class PersonsController {

  @GetMapping("/{personId}")
  public Person get(@PathVariable String personId) {
    return Person.builder()
        .personId(personId)
        .name("Naz")
        .lastname("Viera")
        .birthDate(LocalDate.now())
        .build();
  }


}
