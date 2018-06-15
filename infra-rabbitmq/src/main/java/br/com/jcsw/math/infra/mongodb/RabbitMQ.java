package br.com.jcsw.math.infra.mongodb;

import static br.com.jcsw.math.infra.mongodb.RabbitMQArguments.DLQ_EXCHANGE;
import static br.com.jcsw.math.infra.mongodb.RabbitMQArguments.RETRY_EXCHANGE;
import static br.com.jcsw.math.infra.mongodb.RabbitMQArguments.X_DEAD_LETTER_EXCHANGE;
import static br.com.jcsw.math.infra.mongodb.RabbitMQArguments.X_DEAD_LETTER_ROUTING_KEY;
import static br.com.jcsw.math.infra.mongodb.RabbitMQArguments.X_MESSAGE_TTL;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;

/**
 * @author jalmeida
 *
 * Create queues, exchange and bindings on RabbitMQ
 */
class RabbitMQ {

  static final Map<String, Queue> queues = new HashMap<>();

  private static final Map<String, Exchange> exchanges = new HashMap<>();

  private static final Logger logger = LoggerFactory.getLogger(RabbitMQ.class);


  static void build(RabbitAdmin admin) {

    buildRetryExchange(admin);
    buildDlqExchange(admin);

    RabbitMQResourcesRegister.getRegisteredProducers().values() //
        .forEach(producer -> buildExchange(admin, producer));

    RabbitMQResourcesRegister.getRegisteredConsumers().values() //
        .forEach(consumer -> {

          Queue queue = buildQueue(admin, consumer);
          Queue retryQueue = buildRetryQueue(admin, consumer);
          Queue dlqQueue = buildDlqQueue(admin, consumer);

          buildQueueBinding(admin, consumer, queue);
          buildRetryQueueBinding(admin, consumer, retryQueue);
          buildDlqQueueBinding(admin, consumer, dlqQueue);
        });
  }


  private static void buildRetryExchange(RabbitAdmin admin) {
    Exchange retryExchange = new DirectExchange(RETRY_EXCHANGE, true, false);
    admin.declareExchange(retryExchange);
    exchanges.put(RETRY_EXCHANGE, retryExchange);
  }

  private static void buildDlqExchange(RabbitAdmin admin) {
    Exchange dlqExchange = new DirectExchange(DLQ_EXCHANGE, true, false);
    admin.declareExchange(dlqExchange);
    exchanges.put(DLQ_EXCHANGE, dlqExchange);
  }

  private static void buildExchange(RabbitAdmin admin, Producer producer) {
    logger.info("method=buildExchange {}", producer.identifier());

    Exchange exchange = null;
    switch (producer.getType()) {
      case DIRECT:
        exchange = new DirectExchange(producer.identifier(), true, false);
        break;
      case FANOUT:
        exchange = new FanoutExchange(producer.identifier(), true, false);
        break;
    }

    admin.declareExchange(exchange);
    exchanges.put(producer.identifier(), exchange);
  }

  private static Queue buildQueue(RabbitAdmin admin, Consumer consumer) {
    logger.info("method=buildQueue {}", consumer.identifier());

    Map<String, Object> queueArgs = new HashMap<>();
    queueArgs.put(X_DEAD_LETTER_EXCHANGE, StringUtils.EMPTY);
    queueArgs.put(X_DEAD_LETTER_ROUTING_KEY, consumer.retryIdentifier());
    Queue queue = new Queue(consumer.identifier(), true, false, false, queueArgs);
    admin.declareQueue(queue);
    queues.put(consumer.identifier(), queue);
    return queue;
  }

  private static Queue buildRetryQueue(RabbitAdmin admin, Consumer consumer) {
    logger.info("method=buildRetryQueue {}", consumer.retryIdentifier());

    Map<String, Object> retryQueueArgs = new HashMap<>();
    retryQueueArgs.put(X_DEAD_LETTER_EXCHANGE, StringUtils.EMPTY);
    retryQueueArgs.put(X_MESSAGE_TTL, consumer.getRetryTime());
    Queue retryQueue = new Queue(consumer.retryIdentifier(), true, false, false, retryQueueArgs);
    admin.declareQueue(retryQueue);
    return retryQueue;
  }

  private static Queue buildDlqQueue(RabbitAdmin admin, Consumer consumer) {
    logger.info("method=buildDlqQueue {}", consumer.dlqIdentifier());

    Queue queue = new Queue(consumer.dlqIdentifier(), true, false, false, Collections.emptyMap());
    admin.declareQueue(queue);
    return queue;
  }

  private static void buildQueueBinding(RabbitAdmin admin, Consumer consumer, Queue queue) {
    logger.info("method=buildQueueBinding {} > {}", consumer.producerIdentifier(), consumer.identifier());
    admin.declareBinding(//
        BindingBuilder //
            .bind(queue)//
            .to(exchanges.get(consumer.producerIdentifier()))//
            .with(StringUtils.EMPTY)//
            .noargs());
  }

  private static void buildRetryQueueBinding(RabbitAdmin admin, Consumer consumer, Queue retryQueue) {
    logger.info("method=buildRetryQueueBinding {} > {}", consumer.producerIdentifier(), consumer.retryIdentifier());
    admin.declareBinding(//
        BindingBuilder //
            .bind(retryQueue)//
            .to(exchanges.get(RETRY_EXCHANGE))//
            .with(consumer.identifier())//
            .noargs());
  }

  private static void buildDlqQueueBinding(RabbitAdmin admin, Consumer consumer, Queue dlqQueue) {
    logger.info("method=buildDlqQueueBinding {} > {}", consumer.producerIdentifier(), consumer.dlqIdentifier());
    admin.declareBinding(//
        BindingBuilder //
            .bind(dlqQueue)//
            .to(exchanges.get(DLQ_EXCHANGE))//
            .with(consumer.identifier())//
            .noargs());
  }
}
