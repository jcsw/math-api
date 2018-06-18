package br.com.jcsw.math.infra.mongodb;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "math-operation")
public class MathOperationLogEntity {

  @Id
  private String id;

  private String operation;

  private List<BigDecimal> parameters;

  private BigDecimal result;

  public MathOperationLogEntity() {
    super();
  }

  public MathOperationLogEntity(String operation, List<BigDecimal> parameters, BigDecimal result) {
    this.operation = operation;
    this.parameters = parameters;
    this.result = result;
  }

  @Override
  public boolean equals(Object o) {
    if(this == o) {
      return true;
    }
    if(o == null || getClass() != o.getClass()) {
      return false;
    }
    MathOperationLogEntity that = (MathOperationLogEntity) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getOperation() {
    return operation;
  }

  public void setOperation(String operation) {
    this.operation = operation;
  }

  public List<BigDecimal> getParameters() {
    return parameters;
  }

  public void setParameters(List<BigDecimal> parameters) {
    this.parameters = parameters;
  }

  public BigDecimal getResult() {
    return result;
  }

  public void setResult(BigDecimal result) {
    this.result = result;
  }
}
