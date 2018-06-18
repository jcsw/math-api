package br.com.jcsw.math.mongodb;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "message-fallback")
public class AsyncMessageFallbackEntity {

  @Id
  private String id;

  private String exchange;

  private Object message;

  public AsyncMessageFallbackEntity(String exchange, Object message) {
    this.exchange = exchange;
    this.message = message;
  }

  public AsyncMessageFallbackEntity() {
    super();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
        .append("id", id)
        .append("message", message)
        .toString();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Object getMessage() {
    return message;
  }

  public void setMessage(Object message) {
    this.message = message;
  }

  public String getExchange() {
    return exchange;
  }

  public void setExchange(String exchange) {
    this.exchange = exchange;
  }
}
