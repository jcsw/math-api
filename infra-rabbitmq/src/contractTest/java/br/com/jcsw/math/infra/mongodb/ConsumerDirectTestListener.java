package br.com.jcsw.math.infra.mongodb;

import static br.com.jcsw.math.infra.mongodb.RabbitMQArguments.CONSUMER_PREFIX;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsumerDirectTestListener implements ConsumerListener {

  private static final Logger logger = LoggerFactory.getLogger(ConsumerDirectTestListener.class);

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
    return CONSUMER_PREFIX + "direct_test";
  }
}
