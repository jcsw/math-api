package br.com.jcsw.math.infra.rabbitmq;

/**
 * @author jalmeida
 *
 * Arguments of RabbitMQ
 */
class RabbitMQArguments {

  private static String APP_PREFIX = "math-api.";

  static final String X_MESSAGE_TTL = "x-message-ttl";

  static final String X_DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";

  static final String X_DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

  static String CONSUMER_PREFIX = APP_PREFIX + "queue.";

  static String PRODUCER_PREFIX = APP_PREFIX + "exchange.";

  static String RETRY_SUFFIX = ".retry";

  static String DLQ_SUFFIX = ".dlq";

  static String RETRY_EXCHANGE = APP_PREFIX + "exchange.retry";

  static String DLQ_EXCHANGE = APP_PREFIX + "exchange.dlq";
}
