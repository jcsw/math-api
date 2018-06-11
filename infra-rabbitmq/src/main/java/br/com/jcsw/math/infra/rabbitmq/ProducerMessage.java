package br.com.jcsw.math.infra.rabbitmq;

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

  @Autowired
  private RabbitTemplate rabbitTemplate;


  @HystrixCommand(fallbackMethod = "sendMessageFallback")
  @LogExecutionInfo
  public void sendMessage(Producer exchange, Object message) {
    rabbitTemplate.convertAndSend(exchange.identifier(), StringUtils.EMPTY, message);
  }

  @LogExecutionInfo
  @SuppressWarnings("unused")
  private void sendMessageFallback(Producer exchange, Object message) {
    // TODO implement
  }
}
