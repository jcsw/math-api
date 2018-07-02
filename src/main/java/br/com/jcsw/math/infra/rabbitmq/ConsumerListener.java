package br.com.jcsw.math.infra.rabbitmq;

import static br.com.jcsw.math.infra.rabbitmq.RabbitMQArguments.CONSUMER_PREFIX;

/**
 * Listener for receive message of a consumer
 */
public interface ConsumerListener {

  String SEARCH_ADDRESS = CONSUMER_PREFIX + "search_address";

  String PERSISTENCE_FALLBACK = CONSUMER_PREFIX + "persistence_fallback";

  /**
   * Receive message
   *
   * @param message Object
   */
  void onMessage(Object message);

  /**
   * Receive message on fallback
   *
   * @param message Object
   */
  default void onMessageFallback(Object message) {
    throw new UndefinedFallbackException();
  }

  /**
   * Identifier consumer
   */
  String consumerIdentifier();
}
