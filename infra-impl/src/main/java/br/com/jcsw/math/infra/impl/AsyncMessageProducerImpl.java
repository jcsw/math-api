package br.com.jcsw.math.infra.impl;

import br.com.jcsw.math.OperationRequest;
import br.com.jcsw.math.aop.LogExecutionInfo;
import br.com.jcsw.math.infra.api.AsyncMessageProducer;
import br.com.jcsw.math.infra.mongodb.RabbitMQAsyncMessageProducer;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AsyncMessageProducerImpl implements AsyncMessageProducer {

  @Autowired
  private RabbitMQAsyncMessageProducer rabbitMQAsyncMessageProducer;

  @HystrixCommand(fallbackMethod = "sendMessageToAsyncMathOperationFallback")
  @LogExecutionInfo
  @Override
  public void sendMessageToAsyncMathOperation(OperationRequest operationRequest) {
    rabbitMQAsyncMessageProducer.sendMessage(RabbitMQAsyncMessageProducer.ASYNC_MATH_OPERATION, operationRequest);
  }

  @LogExecutionInfo
  @SuppressWarnings("unused")
  private void sendMessageToAsyncMathOperationFallback(OperationRequest operationRequest) {
    throw new IllegalStateException("Fallback not implemented");
  }

}
