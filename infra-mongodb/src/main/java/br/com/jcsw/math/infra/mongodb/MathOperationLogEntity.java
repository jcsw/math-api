package br.com.jcsw.math.infra.mongodb;

import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "math-operation")
public class MathOperationLogEntity extends AbstractEntity {

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
  public String toString() {
    return new ToStringBuilder(this)
        .append("idt", super.getIdt())
        .append("operation", operation)
        .append("parameters", parameters)
        .append("result", result)
        .toString();
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
