package br.com.jcsw.math.infra.rabbitmq;

import java.util.Map;
import org.apache.commons.lang3.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author jalmeida
 *
 * Receive all messages and send to listener
 */
public class ConsumerMessageListener implements MessageListener {

  private static final Logger logger = LoggerFactory.getLogger(ConsumerMessageListener.class);

  private final RabbitTemplate rabbitTemplate;
  private final Map<String, ConsumerListener> messageConsumerListeners;

  ConsumerMessageListener(RabbitTemplate rabbitTemplate,
      Map<String, ConsumerListener> messageConsumerListeners) {
    this.rabbitTemplate = rabbitTemplate;
    this.messageConsumerListeners = messageConsumerListeners;
  }

  public void onMessage(Message message) {
    MessageProperties properties = message.getMessageProperties();
    Object content = extractContentFromMessage(message);
    ConsumerListener listener = extractListenerFromMessageProperties(properties);

    logger.info("method=onMessage, messageId={}, listener={}, contentClass={}", //
        properties.getMessageId(), //
        listener.consumerIdentifier(), //
        ClassUtils.getSimpleName(content));

    listener.onMessage(content);
  }

  void onMessageFallback(Message message) {
    MessageProperties properties = message.getMessageProperties();
    Object content = extractContentFromMessage(message);
    ConsumerListener listener = extractListenerFromMessageProperties(properties);

    logger.info("method=onMessageFallback, messageId={}, listener={}, contentClass={}", //
        properties.getMessageId(), //
        listener.consumerIdentifier(), //
        ClassUtils.getSimpleName(content));

    listener.onMessageFallback(content);
  }

  private Object extractContentFromMessage(Message message) {
    return rabbitTemplate.getMessageConverter().fromMessage(message);
  }

  private ConsumerListener extractListenerFromMessageProperties(MessageProperties properties) {

    if(messageConsumerListeners.containsKey(properties.getConsumerQueue())) {
      return messageConsumerListeners.get(properties.getConsumerQueue());
    }

    throw new UndefinedConsumerListenerException(properties.getConsumerQueue());
  }
}
