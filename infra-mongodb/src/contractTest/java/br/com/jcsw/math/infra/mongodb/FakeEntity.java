package br.com.jcsw.math.infra.mongodb;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "fake-collection")
public class FakeEntity extends AbstractEntity {

  private String data;

  public FakeEntity() {
    super();
  }

  public FakeEntity(String data) {
    this.data = data;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }
}
