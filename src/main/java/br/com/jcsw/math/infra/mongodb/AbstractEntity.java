package br.com.jcsw.math.infra.mongodb;

import java.util.Objects;
import java.util.UUID;
import org.springframework.data.mongodb.core.index.Indexed;

public abstract class AbstractEntity {

  @Indexed(unique = true)
  private String idt;

  AbstractEntity() {
    this.idt = UUID.randomUUID().toString();
  }

  @Override
  public boolean equals(Object o) {
    if(this == o) {
      return true;
    }
    if(o == null || getClass() != o.getClass()) {
      return false;
    }
    AbstractEntity that = (AbstractEntity) o;
    return Objects.equals(idt, that.idt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idt);
  }

  public String getIdt() {
    return idt;
  }

  public void setIdt(String idt) {
    this.idt = idt;
  }
}
