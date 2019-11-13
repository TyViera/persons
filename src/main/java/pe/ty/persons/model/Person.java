package pe.ty.persons.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.ty.persons.core.BaseTyModel;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person extends BaseTyModel {

  private String personId;
  private String name;
  private String lastname;
  private LocalDate birthDate;

}
