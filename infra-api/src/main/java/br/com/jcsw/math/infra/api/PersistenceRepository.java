package br.com.jcsw.math.infra.api;

import br.com.jcsw.math.OperationRequest;
import java.math.BigDecimal;

public interface PersistenceRepository {

  void persistMathOperationLog(OperationRequest operationRequest, BigDecimal result);

}
