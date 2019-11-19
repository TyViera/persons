package pe.ty.persons.test.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.experimental.UtilityClass;
import pe.ty.persons.model.City;
import pe.ty.persons.model.City.CityBuilder;
import pe.ty.persons.model.Person;
import pe.ty.persons.model.Person.PersonBuilder;
import pe.ty.test.util.FakeDataUtil;

@UtilityClass
public class PersonsFakeDataUtil {

  public Person getFakePerson(boolean couldBeNull, boolean couldBeEmpty) {
    if (couldBeNull && FakeDataUtil.faker.bool().bool()) {
      return null;
    }
    if (couldBeEmpty && FakeDataUtil.faker.bool().bool()) {
      return Person.EMPTY;
    }
    PersonBuilder builder = Person.builder();
    builder.personId(FakeDataUtil.faker.idNumber().valid());
    if (FakeDataUtil.faker.bool().bool()) {
      builder.name(FakeDataUtil.faker.superhero().name());
    }
    if (FakeDataUtil.faker.bool().bool()) {
      builder.lastName(FakeDataUtil.faker.superhero().suffix());
    }
    if (FakeDataUtil.faker.bool().bool()) {
      builder.birthDate(
          LocalDateTime
              .ofInstant(Instant.ofEpochMilli(FakeDataUtil.faker.date().birthday().getTime()),
                  ZoneId.systemDefault()).toLocalDate());
    }
    if (FakeDataUtil.faker.bool().bool()) {
      builder.city(getFakeCity(FakeDataUtil.faker.bool().bool(), FakeDataUtil.faker.bool().bool()));
    }
    return builder.build();
  }

  public City getFakeCity(boolean couldBeNull, boolean couldBeEmpty) {
    if (couldBeNull && FakeDataUtil.faker.bool().bool()) {
      return null;
    }
    if (couldBeEmpty && FakeDataUtil.faker.bool().bool()) {
      return City.EMPTY;
    }
    CityBuilder builder = City.builder();
    builder.cityId(FakeDataUtil.faker.number().randomNumber());
    if (FakeDataUtil.faker.bool().bool()) {
      builder.name(FakeDataUtil.faker.address().city());
    }
    if (FakeDataUtil.faker.bool().bool()) {
      builder.alias(FakeDataUtil.faker.address().cityName());
    }
    if (FakeDataUtil.faker.bool().bool()) {
      builder.postalCode(FakeDataUtil.faker.address().zipCode());
    }
    return builder.build();
  }

}
