package br.com.jcsw.math;

import java.math.BigDecimal;
import java.util.List;
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
