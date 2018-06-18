package br.com.jcsw.math.infra.rabbitmq;

import static br.com.jcsw.math.infra.rabbitmq.RabbitMQArguments.PRODUCER_PREFIX;

/**
 * Listener for receive message of a consumer
 */
public interface ConsumerListener {

  String ASYNC_MATH_OPERATION = PRODUCER_PREFIX + "async_math_operation";

  String PERSISTENCE_FALLBACK = PRODUCER_PREFIX + "persistence_fallback";

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
