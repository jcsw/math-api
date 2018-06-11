package br.com.jcsw.math.infra.rabbitmq;

/**
 * Listener for receive message of a consumer
 */
public interface ConsumerListener {

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
