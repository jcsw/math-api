package br.com.jcsw.math.infra.rabbitmq;

import static br.com.jcsw.math.infra.rabbitmq.RabbitMQArguments.APP_PREFIX;

/**
 * @author jalmeida
 *
 * Registration of all producers
 */
public enum Producer {

  // Producers of tests
  PRODUCER_DIRECT_TEST(ProducerType.DIRECT),
  PRODUCER_FANOUT_TEST(ProducerType.FANOUT),
  //
  ASYNC_OPERATION(ProducerType.DIRECT);

  private final ProducerType type;

  Producer(ProducerType type) {
    this.type = type;
  }

  public ProducerType getType() {
    return type;
  }

  public String identifier() {
    return APP_PREFIX + type.name().toLowerCase() + "." + name().toLowerCase();
  }

  public enum ProducerType {
    DIRECT, FANOUT
  }
}
