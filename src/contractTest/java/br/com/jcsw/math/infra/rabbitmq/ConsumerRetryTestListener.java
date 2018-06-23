package br.com.jcsw.math.infra.rabbitmq;

import static br.com.jcsw.math.infra.rabbitmq.RabbitMQArguments.CONSUMER_PREFIX;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsumerRetryTestListener implements ConsumerListener {

  private static final Logger logger = LoggerFactory.getLogger(ConsumerRetryTestListener.class);

  @Autowired
  private ConsumerMessageVerify consumerMessageVerify;

  @Override
  public void onMessage(Object message) {
    logger.info("method=onMessage message={}", message);
    consumerMessageVerify.addMessageConsumeAttempt(consumerIdentifier(), message);

    if(consumerMessageVerify.countMessageConsumeAttempts(consumerIdentifier(), message) < 3) {
      throw new RuntimeException("retry");
    }

    consumerMessageVerify.addMessageConsumed(consumerIdentifier(), message);
  }

  @Override
  public String consumerIdentifier() {
    return CONSUMER_PREFIX + "retry_test";
  }
}
