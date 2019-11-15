package pe.ty.persons.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.ty.persons.core.model.BaseTyModel;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class City extends BaseTyModel {

  private final static City EMPTY = new City();

  private Long cityId;
  private String name;
  private String alias;
  private String postalCode;

  @Override
  public City emptyChild() {
    return EMPTY;
  }
}
