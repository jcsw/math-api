package br.com.jcsw.math.infra.rabbitmq;

/**
 * @author jalmeida
 *
 * Arguments of RabbitMQ
 */
class RabbitMQArguments {

  static final String X_MESSAGE_TTL = "x-message-ttl";

  static final String X_DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";

  static final String X_DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

  static String APP_PREFIX = "jcsw.";

  static String CONSUMER_PREFIX = "queue.";

  static String RETRY_SUFFIX = ".retry";

  static String DLQ_SUFFIX = ".dlq";

  static String RETRY_EXCHANGE = APP_PREFIX + "retry.exchange";

  static String DLQ_EXCHANGE = APP_PREFIX + "dlq.exchange";
}
