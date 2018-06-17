package br.com.jcsw.math.infra.impl;


import br.com.jcsw.math.infra.featuretoggle.FeatureToggleHttpClient;
import br.com.jcsw.math.infra.mongodb.RabbitMQAsyncMessageProducer;
import br.com.jcsw.math.mongodb.MathOperationRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan
@EnableCircuitBreaker
@EnableAspectJAutoProxy
public class TestConfiguration {

  @MockBean
  private MathOperationRepository mathOperationRepository;

  @MockBean
  private RabbitMQAsyncMessageProducer rabbitMQAsyncMessageProducer;

  @MockBean
  private FeatureToggleHttpClient featureToggleHttpClient;

}
