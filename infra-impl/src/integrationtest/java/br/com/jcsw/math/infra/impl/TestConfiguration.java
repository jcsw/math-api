package br.com.jcsw.math.infra.impl;


import br.com.jcsw.math.infra.mongodb.RabbitMQProducerMessage;
import br.com.jcsw.math.mongodb.MathOperationRepository;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableCircuitBreaker
@EnableAutoConfiguration
public class TestConfiguration {

  @MockBean
  private MathOperationRepository mathOperationRepository;

  @MockBean
  private RabbitMQProducerMessage rabbitMQProducerMessage;

}
