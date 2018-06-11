package br.com.jcsw.math;

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
