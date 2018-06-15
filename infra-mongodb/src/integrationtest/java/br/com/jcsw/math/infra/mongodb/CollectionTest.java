package br.com.jcsw.math.infra.mongodb;

import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "collection-test")
public class CollectionTest {

  @Id
  private String id;

  private String data;

  public CollectionTest() {
    super();
  }

  public CollectionTest(String data) {
    this.data = data;
  }

  @Override
  public boolean equals(Object o) {
    if(this == o) {
      return true;
    }
    if(o == null || getClass() != o.getClass()) {
      return false;
    }
    CollectionTest that = (CollectionTest) o;
    return Objects.equals(data, that.data);
  }

  @Override
  public int hashCode() {

    return Objects.hash(data);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }
}
