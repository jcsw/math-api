package br.com.jcsw.math.infra.rabbitmq;

import static br.com.jcsw.math.infra.rabbitmq.RabbitMQArguments.APP_PREFIX;
import static br.com.jcsw.math.infra.rabbitmq.RabbitMQArguments.CONSUMER_PREFIX;
import static br.com.jcsw.math.infra.rabbitmq.RabbitMQArguments.DLQ_SUFFIX;
import static br.com.jcsw.math.infra.rabbitmq.RabbitMQArguments.RETRY_SUFFIX;

import java.util.Arrays;

/**
 * @author jalmeida
 *
 * Registration of all consumers
 */
public enum Consumer {

  // Consumers of tests
  CONSUMER_DIRECT_TEST(Producer.PRODUCER_DIRECT_TEST, 3, 10),
  CONSUMER_FANOUT_TEST_1(Producer.PRODUCER_FANOUT_TEST, 3, 10),
  CONSUMER_FANOUT_TEST_2(Producer.PRODUCER_FANOUT_TEST, 3, 10),
  //
  ASYNC_OPERATION(Producer.ASYNC_OPERATION, 2, 3000);

  private final Producer producer;
  private final int retryQty;
  private final int retryTime;

  Consumer(Producer producer, int retryQty, int retryTime) {
    this.producer = producer;
    this.retryQty = retryQty;
    this.retryTime = retryTime;
  }

  public static Consumer convertByIdentifier(String identifier) {
    return Arrays.stream(Consumer.values())
        .filter(c -> c.identifier().equals(identifier))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(
            String.format("Invalid consumerIdentifier > %s", identifier)));
  }

  public int getRetryQty() {
    return retryQty;
  }

  public int getRetryTime() {
    return retryTime;
  }

  public String identifier() {
    return APP_PREFIX + CONSUMER_PREFIX + name().toLowerCase();
  }

  public String retryIdentifier() {
    return identifier() + RETRY_SUFFIX;
  }

  public String dlqIdentifier() {
    return identifier() + DLQ_SUFFIX;
  }

  public String producerIdentifier() {
    return producer.identifier();
  }
}
