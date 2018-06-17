package br.com.jcsw.math;


import br.com.jcsw.math.infra.api.FeatureToggle;
import br.com.jcsw.math.infra.api.PersistenceRepository;
import br.com.jcsw.math.infra.api.AsyncMessageProducer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class TestConfiguration {

  @MockBean
  private AsyncMessageProducer asyncMessageProducer;

  @MockBean
  private FeatureToggle featureToggle;

  @MockBean
  private PersistenceRepository persistenceRepository;

}
