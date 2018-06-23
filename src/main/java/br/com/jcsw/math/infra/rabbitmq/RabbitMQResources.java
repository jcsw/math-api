package br.com.jcsw.math.infra.rabbitmq;

import static br.com.jcsw.math.infra.rabbitmq.RabbitMQArguments.CONSUMER_PREFIX;
import static br.com.jcsw.math.infra.rabbitmq.RabbitMQArguments.DLQ_SUFFIX;
import static br.com.jcsw.math.infra.rabbitmq.RabbitMQArguments.PRODUCER_PREFIX;
import static br.com.jcsw.math.infra.rabbitmq.RabbitMQArguments.RETRY_SUFFIX;

import java.util.List;

class RabbitMQResources {

  private List<Producer> producers;
  private List<Consumer> consumers;

  public List<Producer> getProducers() {
    return producers;
  }

  public void setProducers(List<Producer> producers) {
    this.producers = producers;
  }

  public List<Consumer> getConsumers() {
    return consumers;
  }

  public void setConsumers(List<Consumer> consumers) {
    this.consumers = consumers;
  }

}

enum ProducerType {
  DIRECT, FANOUT
}

class Producer {

  private String name;
  private ProducerType type;

  public String identifier() {
    return PRODUCER_PREFIX + name.toLowerCase();
  }

  public void setName(String name) {
    this.name = name;
  }

  public ProducerType getType() {
    return type;
  }

  public void setType(ProducerType type) {
    this.type = type;
  }
}

class Consumer {

  private String name;
  private String producer;
  private Integer retryQty;
  private Integer retryTime;

  public String identifier() {
    return CONSUMER_PREFIX + name.toLowerCase();
  }

  public String producerIdentifier() {
    return PRODUCER_PREFIX + producer.toLowerCase();
  }

  public String retryIdentifier() {
    return identifier() + RETRY_SUFFIX;
  }

  public String dlqIdentifier() {
    return identifier() + DLQ_SUFFIX;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setProducer(String producer) {
    this.producer = producer;
  }

  public Integer getRetryQty() {
    return retryQty;
  }

  public void setRetryQty(Integer retryQty) {
    this.retryQty = retryQty;
  }

  public Integer getRetryTime() {
    return retryTime;
  }

  public void setRetryTime(Integer retryTime) {
    this.retryTime = retryTime;
  }
}

