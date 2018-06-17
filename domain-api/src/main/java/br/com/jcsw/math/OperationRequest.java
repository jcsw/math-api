package br.com.jcsw.math;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class OperationRequest {

  private MathOperation operation;

  private List<BigDecimal> parameters;

  public OperationRequest(MathOperation operation, List<BigDecimal> parameters) {
    this.operation = operation;
    this.parameters = parameters;
  }

  public OperationRequest() {
    super();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
        .append("operation", operation)
        .append("parameters", parameters)
        .toString();
  }

  @Override
  public boolean equals(Object o) {
    if(this == o) {
      return true;
    }
    if(o == null || getClass() != o.getClass()) {
      return false;
    }
    OperationRequest that = (OperationRequest) o;
    return operation == that.operation &&
        Objects.equals(parameters, that.parameters);
  }

  @Override
  public int hashCode() {

    return Objects.hash(operation, parameters);
  }

  public MathOperation getOperation() {
    return operation;
  }

  public void setOperation(MathOperation operation) {
    this.operation = operation;
  }

  public List<BigDecimal> getParameters() {
    return parameters;
  }

  public void setParameters(List<BigDecimal> parameters) {
    this.parameters = parameters;
  }
}
