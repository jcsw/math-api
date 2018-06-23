package br.com.jcsw.math.infra.rabbitmq;

import static org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer.X_EXCEPTION_MESSAGE;
import static org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer.X_EXCEPTION_STACKTRACE;
import static org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer.X_ORIGINAL_EXCHANGE;
import static org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer.X_ORIGINAL_ROUTING_KEY;

import java.util.Map;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;

/**
 * @author jalmeida
 *
 * Apply message recovery
 */
public class ConsumerMessageRecoverer implements MessageRecoverer {

  private static final Logger logger = LoggerFactory.getLogger(ConsumerMessageRecoverer.class);

  private static final String X_RETRIES_HEADER = "x-retries";

  private final RabbitTemplate rabbitTemplate;

  private final Map<String, ConsumerListener> messageConsumerListeners;

  ConsumerMessageRecoverer(RabbitTemplate rabbitTemplate, Map<String, ConsumerListener> messageConsumerListeners) {
    this.rabbitTemplate = rabbitTemplate;
    this.messageConsumerListeners = messageConsumerListeners;
  }

  @Override
  public void recover(Message message, Throwable cause) {
    logger.info("method=recover message={} cause={}",
        message.getMessageProperties().getConsumerQueue(),
        ExceptionUtils.getRootCauseMessage(cause));

    try {
      redelivery(message, cause);
    } catch (Exception ex) {
      logger.error("method=recover, label=UNEXPECTED_ERROR", ex);
    }
  }

  private void redelivery(Message message, Throwable cause) {
    boolean redelivery = false;

    Map<String, Object> headers = message.getMessageProperties().getHeaders();
    Integer retriesHeader = (Integer) headers.get(X_RETRIES_HEADER);

    if(retriesHeader == null) {
      retriesHeader = NumberUtils.INTEGER_ZERO;
    }

    String queue = message.getMessageProperties().getConsumerQueue();
    Consumer consumer = RabbitMQResourcesRegister.getRegisteredConsumers().get(queue);

    if(retriesHeader < consumer.getRetryQty()) {
      headers.put(X_RETRIES_HEADER, retriesHeader + 1);
      headers.put(X_EXCEPTION_MESSAGE, ExceptionUtils.getRootCauseMessage(cause));
      headers.put(X_EXCEPTION_STACKTRACE, ExceptionUtils.getStackTrace(cause));
      headers.put(X_ORIGINAL_EXCHANGE, message.getMessageProperties().getReceivedExchange());
      headers.put(X_ORIGINAL_ROUTING_KEY, message.getMessageProperties().getReceivedRoutingKey());

      rabbitTemplate.send(RabbitMQArguments.RETRY_EXCHANGE, queue, message);

      redelivery = true;
    } else {

      try {
        new ConsumerMessageListener(rabbitTemplate, messageConsumerListeners).onMessageFallback(message);
      } catch (Exception ex) {
        logger.warn("method=redelivery label=SEND_TO_DLQ queue={} retriesHeader={}", queue, retriesHeader, ex);
        rabbitTemplate.send(RabbitMQArguments.DLQ_EXCHANGE, queue, message);
      }

    }

    logger.info("method=redelivery queue={} retriesHeader={} redelivery={}", queue, retriesHeader, redelivery);
  }
}
