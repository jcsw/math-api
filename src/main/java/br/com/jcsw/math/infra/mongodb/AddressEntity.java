package br.com.jcsw.math.infra.mongodb;

import java.util.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "address")
public class AddressEntity extends AbstractEntity {

  private String zipCode;

  private Map<String, Object> address;

  public AddressEntity() {
    super();
  }

  public AddressEntity(String zipCode, Map<String, Object> address) {
    this.zipCode = zipCode;
    this.address = address;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
        .append("zipCode", zipCode)
        .append("address", address)
        .toString();
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public Map<String, Object> getAddress() {
    return address;
  }

  public void setAddress(Map<String, Object> address) {
    this.address = address;
  }
}
