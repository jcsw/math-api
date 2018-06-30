package br.com.jcsw.math.domain.api;

/**
 * Service to math
 */
public interface MathService {

  /**
   * Execute math operation
   *
   * @param operationRequest OperationRequest
   * @return OperationResponse
   */
  OperationResponse executeMathOperation(OperationRequest operationRequest);
}
