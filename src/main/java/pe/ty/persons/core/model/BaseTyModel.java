package pe.ty.persons.core.model;

import java.io.Serializable;

public abstract class BaseTyModel implements Serializable {

  @Override
  public abstract int hashCode();

  @Override
  public abstract boolean equals(Object obj);

  protected BaseTyModel emptyChild() {
    return null;
  }

  public boolean isEmpty() {
    return this.equals(emptyChild());
  }

}
