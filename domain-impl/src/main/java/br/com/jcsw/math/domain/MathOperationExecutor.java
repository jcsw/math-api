package br.com.jcsw.math.domain;

import java.math.BigDecimal;
import java.util.List;

class MathOperationExecutor {

  static BigDecimal execute(MathOperation operation, List<BigDecimal> parameters) {

    switch (operation) {
      case SUM:
        return parameters.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
      default:
        throw new IllegalArgumentException("Invalid operation > " + operation);

    }
  }

}
