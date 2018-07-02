package br.com.jcsw.math.infra;

import static br.com.jcsw.math.infra.rabbitmq.RabbitMQAsyncMessageProducer.PERSISTENCE_FALLBACK;
import static br.com.jcsw.math.infra.rabbitmq.RabbitMQAsyncMessageProducer.SEARCH_ADDRESS;

import br.com.jcsw.math.aop.LogExecutionInfo;
import br.com.jcsw.math.infra.api.AsyncMessageProducer;
import br.com.jcsw.math.infra.mongodb.AsyncMessageFallbackEntity;
import br.com.jcsw.math.infra.mongodb.AsyncMessageFallbackRepository;
import br.com.jcsw.math.infra.rabbitmq.RabbitMQAsyncMessageProducer;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class AsyncMessageProducerImpl implements AsyncMessageProducer {

  @Autowired
  private RabbitMQAsyncMessageProducer rabbitMQAsyncMessageProducer;

  @Autowired
  private AsyncMessageFallbackRepository asyncMessageFallbackRepository;

  @HystrixCommand(fallbackMethod = "sendMessageToSearchAddressFallback")
  @LogExecutionInfo
  @Override
  public void sendMessageToSearchAddress(String zipCode) {
    rabbitMQAsyncMessageProducer.sendMessage(SEARCH_ADDRESS, zipCode);
  }

  @LogExecutionInfo
  @SuppressWarnings("unused")
  private void sendMessageToSearchAddressFallback(String zipCode) {
    asyncMessageFallbackRepository.insert(new AsyncMessageFallbackEntity(SEARCH_ADDRESS, zipCode));
  }

  @Override
  public void sendMessageToPersistenceFallback(Object entity) {
    rabbitMQAsyncMessageProducer.sendMessage(PERSISTENCE_FALLBACK, entity);
  }

}
