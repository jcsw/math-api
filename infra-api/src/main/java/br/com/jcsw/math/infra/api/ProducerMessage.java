package br.com.jcsw.math.infra.api;

public interface ProducerMessage {

  void sendMessageToSendAsyncOperation(Object message);
}
