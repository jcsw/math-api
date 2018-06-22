package br.com.jcsw.math.infra.rabbitmq;

import java.util.Date;
import java.util.Objects;

public class MessageVO {

  private String value;

  private Date date;

  public MessageVO(String value, Date date) {
    this.value = value;
    this.date = new Date(date.getTime());
  }

  public MessageVO() {
    super();
  }

  @Override
  public boolean equals(Object o) {
    if(this == o) {
      return true;
    }
    if(o == null || getClass() != o.getClass()) {
      return false;
    }
    MessageVO messageVO = (MessageVO) o;
    return Objects.equals(value, messageVO.value) &&
        Objects.equals(date, messageVO.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, date);
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public Date getDate() {
    return new Date(date.getTime());
  }

  public void setDate(Date date) {
    this.date = new Date(date.getTime());
  }
}
