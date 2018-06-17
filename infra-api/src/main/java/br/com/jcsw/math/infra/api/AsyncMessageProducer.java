package br.com.jcsw.math.infra.api;

import br.com.jcsw.math.OperationRequest;

public interface AsyncMessageProducer {

  void sendMessageToAsyncMathOperation(OperationRequest operationRequest);
}
