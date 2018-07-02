package br.com.jcsw.math.chaosmonkey;

import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChaosMonkeyConfiguration {

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnEnabledEndpoint
  public ChaosMonkeyRestEndpoint chaosMonkeyRestEndpoint() {
    return new ChaosMonkeyRestEndpoint();
  }

}
