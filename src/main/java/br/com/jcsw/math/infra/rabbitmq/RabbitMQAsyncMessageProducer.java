package br.com.jcsw.math.infra.rabbitmq;

import static br.com.jcsw.math.infra.rabbitmq.RabbitMQArguments.PRODUCER_PREFIX;

import br.com.jcsw.math.aop.LogExecutionInfo;
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
public class RabbitMQAsyncMessageProducer {

  public static final String SEARCH_ADDRESS = PRODUCER_PREFIX + "search_address";

  public static final String PERSISTENCE_FALLBACK = PRODUCER_PREFIX + "persistence_fallback";

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @LogExecutionInfo
  public void sendMessage(String producer, Object message) {
    rabbitTemplate.convertAndSend(producer, StringUtils.EMPTY, message);
  }

}
