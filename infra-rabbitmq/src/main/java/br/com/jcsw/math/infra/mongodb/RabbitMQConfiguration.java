package br.com.jcsw.math.infra.mongodb;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.retry.backoff.NoBackOffPolicy;
import org.springframework.retry.policy.NeverRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author jalmeida
 *
 * RabbitMQ Configuration
 */
@Configuration
@EnableRabbit
@EnableTransactionManagement
public class RabbitMQConfiguration implements RabbitListenerConfigurer {

  private static final Logger logger = LoggerFactory.getLogger(RabbitMQConfiguration.class);

  private RabbitTemplate rabbitTemplate;

  @Autowired
  private ConsumerListenerRegister consumerListeners;

  @Override
  public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
    registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
  }

  private DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
    DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
    factory.setMessageConverter(consumerJackson2MessageConverter());
    return factory;
  }

  private MappingJackson2MessageConverter consumerJackson2MessageConverter() {
    return new MappingJackson2MessageConverter();
  }

  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {

    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());

    try {
      RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
      RabbitMQ.build(rabbitAdmin);
    } catch (AmqpException ex) {
      logger.warn("label=COULD_NOT_CONNECT_TO_RABBITMQ", ex);
    }

    this.rabbitTemplate = rabbitTemplate;
    return rabbitTemplate;
  }

  @Bean
  public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {

    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setAcknowledgeMode(AcknowledgeMode.AUTO);
    container.setMessageConverter(producerJackson2MessageConverter());

    RabbitMQ.queues.keySet().forEach(container::addQueueNames);

    container.setMessageListener(
        new ConsumerMessageListener(rabbitTemplate, consumerListeners.getRegisteredConsumerListeners()));

    container.setAdviceChain(//
        RetryInterceptorBuilder.StatefulRetryInterceptorBuilder.stateless()
            .retryOperations(retryTemplate())
            .recoverer(new ConsumerMessageRecoverer(rabbitTemplate, consumerListeners.getRegisteredConsumerListeners()))
            .build());

    return container;
  }

  private Jackson2JsonMessageConverter producerJackson2MessageConverter() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
    mapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter(mapper);
    messageConverter.setCreateMessageIds(true);
    return messageConverter;
  }

  private RetryTemplate retryTemplate() {
    RetryTemplate template = new RetryTemplate();
    template.setRetryPolicy(new NeverRetryPolicy());
    template.setBackOffPolicy(new NoBackOffPolicy());
    return template;
  }

}
