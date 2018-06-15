package br.com.jcsw.math.infra.impl;

import br.com.jcsw.math.aop.LogExecutionInfo;
import br.com.jcsw.math.infra.api.ProducerMessage;
import br.com.jcsw.math.infra.mongodb.RabbitMQProducerMessage;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProducerMessageImpl implements ProducerMessage {

  @Autowired
  private RabbitMQProducerMessage rabbitMQProducerMessage;

  @HystrixCommand(fallbackMethod = "sendMessageFallback")
  @LogExecutionInfo
  @Override
  public void sendMessageToSendAsyncOperation(Object message) {
    rabbitMQProducerMessage.sendMessage(RabbitMQProducerMessage.ASYNC_OPERATION, message);
  }

  @LogExecutionInfo
  @SuppressWarnings("unused")
  private void sendMessageFallback(String producer, Object message) {
    throw new IllegalStateException("Not implemented");
  }

}
