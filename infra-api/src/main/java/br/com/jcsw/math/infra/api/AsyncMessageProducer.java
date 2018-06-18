package br.com.jcsw.math.infra.api;

import br.com.jcsw.math.domain.OperationRequest;

public interface AsyncMessageProducer {

  void sendMessageToAsyncMathOperation(OperationRequest operationRequest);

  void sendMessageToPersistenceFallback(Object entity);
}
