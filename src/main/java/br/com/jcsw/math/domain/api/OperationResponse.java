package br.com.jcsw.math.domain.api;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class OperationResponse {

  private BigDecimal result;

  public OperationResponse(BigDecimal result) {
    this.result = result;
  }

  public OperationResponse() {
    super();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
        .append("result", result)
        .toString();
  }

  public BigDecimal getResult() {
    return result;
  }

  public void setResult(BigDecimal result) {
    this.result = result;
  }
}
