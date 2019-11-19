package pe.ty.persons.business;

import pe.ty.persons.model.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonService {

  Flux<Person> getPersons();

  Mono<Person> getPersonById(String personId);

}
