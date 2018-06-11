package br.com.jcsw.math.infra.rabbitmq.listeners;

import br.com.jcsw.math.infra.rabbitmq.Consumer;
import br.com.jcsw.math.infra.rabbitmq.ConsumerListener;
import br.com.jcsw.math.infra.rabbitmq.ConsumerMessageVerify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsumerFanoutTestTwoListener implements ConsumerListener {

  private static final Logger logger = LoggerFactory.getLogger(ConsumerFanoutTestTwoListener.class);

  @Autowired
  private ConsumerMessageVerify consumerMessageVerify;

  @Override
  public void onMessage(Object message) {
    logger.info("method=onMessage message={}", message);
    consumerMessageVerify.addMessageConsumeAttempt(consumerIdentifier(), message);

    consumerMessageVerify.addMessageConsumed(consumerIdentifier(), message);
  }

  @Override
  public String consumerIdentifier() {
    return Consumer.CONSUMER_FANOUT_TEST_2.identifier();
  }
}
