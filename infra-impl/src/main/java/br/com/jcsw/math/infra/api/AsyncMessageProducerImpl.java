package br.com.jcsw.math.infra.api;

import static br.com.jcsw.math.infra.rabbitmq.ConsumerListener.ASYNC_MATH_OPERATION;
import static br.com.jcsw.math.infra.rabbitmq.ConsumerListener.PERSISTENCE_FALLBACK;

import br.com.jcsw.math.aop.LogExecutionInfo;
import br.com.jcsw.math.domain.OperationRequest;
import br.com.jcsw.math.infra.rabbitmq.RabbitMQAsyncMessageProducer;
import br.com.jcsw.math.infra.mongodb.AsyncMessageFallbackEntity;
import br.com.jcsw.math.infra.mongodb.AsyncMessageFallbackRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AsyncMessageProducerImpl implements AsyncMessageProducer {

  @Autowired
  private RabbitMQAsyncMessageProducer rabbitMQAsyncMessageProducer;

  @Autowired
  private AsyncMessageFallbackRepository asyncMessageFallbackRepository;

  @HystrixCommand(fallbackMethod = "sendMessageToAsyncMathOperationFallback")
  @LogExecutionInfo
  @Override
  public void sendMessageToAsyncMathOperation(OperationRequest operationRequest) {
    rabbitMQAsyncMessageProducer.sendMessage(ASYNC_MATH_OPERATION, operationRequest);
  }

  @LogExecutionInfo
  @SuppressWarnings("unused")
  private void sendMessageToAsyncMathOperationFallback(OperationRequest operationRequest) {
    asyncMessageFallbackRepository.insert(new AsyncMessageFallbackEntity(ASYNC_MATH_OPERATION, operationRequest));
  }

  @Override
  public void sendMessageToPersistenceFallback(Object entity) {
    rabbitMQAsyncMessageProducer.sendMessage(PERSISTENCE_FALLBACK, entity);
  }

}
