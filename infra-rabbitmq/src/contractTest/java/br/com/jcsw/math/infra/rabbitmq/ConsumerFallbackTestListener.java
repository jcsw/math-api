package br.com.jcsw.math.infra.rabbitmq;

import static br.com.jcsw.math.infra.rabbitmq.RabbitMQArguments.CONSUMER_PREFIX;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsumerFallbackTestListener implements ConsumerListener {

  private static final Logger logger = LoggerFactory.getLogger(ConsumerFallbackTestListener.class);

  @Autowired
  private ConsumerMessageVerify consumerMessageVerify;

  @Override
  public void onMessage(Object message) {
    logger.info("method=onMessage message={}", message);
    consumerMessageVerify.addMessageConsumeAttempt(consumerIdentifier(), message);
    throw new RuntimeException("fallback");
  }

  @Override
  public void onMessageFallback(Object message) {
    consumerMessageVerify.addMessageFallback(consumerIdentifier(), message);
  }

  @Override
  public String consumerIdentifier() {
    return CONSUMER_PREFIX + "fallback_test";
  }
}
