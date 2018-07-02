package br.com.jcsw.math.infra;

import br.com.jcsw.math.infra.correio.CorreioHttpClient;
import br.com.jcsw.math.infra.mongodb.AddressRepository;
import br.com.jcsw.math.infra.mongodb.AsyncMessageFallbackRepository;
import br.com.jcsw.math.infra.mongodb.MathOperationRepository;
import br.com.jcsw.math.infra.rabbitmq.RabbitMQAsyncMessageProducer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan
@EnableCircuitBreaker
@EnableAspectJAutoProxy
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class TestConfiguration {

  @MockBean
  private MathOperationRepository mathOperationRepository;

  @MockBean
  private RabbitMQAsyncMessageProducer rabbitMQAsyncMessageProducer;

  @MockBean
  private CorreioHttpClient correioHttpClient;

  @MockBean
  private AsyncMessageFallbackRepository asyncMessageFallbackRepository;

  @MockBean
  private AddressRepository addressRepository;


}
