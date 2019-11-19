package pe.ty.persons.expose.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.ty.core.exception.CoreException;
import pe.ty.core.exception.CoreExceptionStatus;
import pe.ty.persons.business.PersonService;
import pe.ty.persons.model.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("${application.rest.base-url}/" + PersonsController.RESOURCE_NAME)
public class PersonsController {

  public static final String RESOURCE_NAME = "persons";

  private PersonService personService;

  @GetMapping
  public Flux<Person> getPersons() throws CoreException {
    return personService.getPersons().switchIfEmpty(notFound());
  }

  private <E> Flux<E> notFound() {

    org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler a;

    return Flux.error(CoreException.builder().status(CoreExceptionStatus.NOT_FOUND).build());
  }

  @GetMapping("/{personId}")
  public Mono<Person> getPersonById(@PathVariable String personId) {
    return personService.getPersonById(personId);
  }

}
