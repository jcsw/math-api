package br.com.jcsw.math.infra.rabbitmq;

import static br.com.jcsw.math.infra.rabbitmq.RabbitMQArguments.PRODUCER_PREFIX;

import br.com.jcsw.math.aop.LogExecutionInfo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jalmeida
 *
 * Produces message to a producer
 */
@Component
public class ProducerMessage {

  public static final String ASYNC_OPERATION = PRODUCER_PREFIX + "async.operation";

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @HystrixCommand(fallbackMethod = "sendMessageFallback")
  @LogExecutionInfo
  public void sendMessage(String producer, Object message) {
    rabbitTemplate.convertAndSend(producer, StringUtils.EMPTY, message);
  }

  @LogExecutionInfo
  @SuppressWarnings("unused")
  private void sendMessageFallback(String producer, Object message) {
    // TODO implement
  }
}
