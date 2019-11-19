package pe.ty.persons.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.ty.core.jackson.NonEmptyModelFilter;
import pe.ty.core.model.BaseTyModel;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Person extends BaseTyModel {

  public final static Person EMPTY = new Person();

  private String personId;
  private String name;
  private String lastName;
  private LocalDate birthDate;

  @JsonInclude(value = Include.CUSTOM, valueFilter = NonEmptyModelFilter.class)
  private City city;

}
