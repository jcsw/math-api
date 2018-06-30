package br.com.jcsw.math.domain;

import br.com.jcsw.math.domain.api.MathOperation;
import java.math.BigDecimal;
import java.util.List;

class MathExecutor {

  static BigDecimal execute(MathOperation operation, List<BigDecimal> parameters) {

    switch (operation) {
      case SUM:
        return parameters.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
      case SUB:
        return parameters.stream().reduce(BigDecimal::subtract).orElse(BigDecimal.ZERO);
      default:
        throw new IllegalArgumentException("Invalid operation > " + operation);
    }
  }

}
