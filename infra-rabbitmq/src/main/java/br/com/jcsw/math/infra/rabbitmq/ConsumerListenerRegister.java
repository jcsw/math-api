package br.com.jcsw.math.infra.rabbitmq;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jalmeida
 *
 * Register consumers listeners
 */
@Component
public class ConsumerListenerRegister implements InitializingBean {

  private static final Logger logger = LoggerFactory.getLogger(ConsumerListenerRegister.class);

  private static Map<String, ConsumerListener> registeredConsumerListeners;

  @Autowired
  private List<ConsumerListener> consumerListeners;

  @Override
  public void afterPropertiesSet() {

    if(registeredConsumerListeners == null) {
      registeredConsumerListeners = consumerListeners.stream()
          .collect(Collectors.toMap(ConsumerListener::consumerIdentifier, l -> l));
      logger.info("registeredConsumerListeners > {}", registeredConsumerListeners);

    }
  }

  Map<String, ConsumerListener> getRegisteredConsumerListeners() {
    return registeredConsumerListeners;
  }

}
