package br.com.jcsw.math.infra.mongodb;

import static br.com.jcsw.math.infra.mongodb.RabbitMQArguments.CONSUMER_PREFIX;

/**
 * Listener for receive message of a consumer
 */
public interface ConsumerListener {

  String ASYNC_MATH_OPERATION = CONSUMER_PREFIX + "async_math_operation";

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
