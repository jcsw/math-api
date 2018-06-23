package br.com.jcsw.math.application;


import br.com.jcsw.math.infra.api.AsyncMessageProducer;
import br.com.jcsw.math.infra.api.Address;
import br.com.jcsw.math.infra.api.PersistenceRepository;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"br.com.jcsw.math.domain", "br.com.jcsw.math.application"})
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class TestConfiguration {

  @MockBean
  private AsyncMessageProducer asyncMessageProducer;

  @MockBean
  private Address address;

  @MockBean
  private PersistenceRepository persistenceRepository;

}
