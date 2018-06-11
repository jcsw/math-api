package br.com.jcsw.math.infra.rabbitmq.listeners;

import br.com.jcsw.math.infra.rabbitmq.Consumer;
import br.com.jcsw.math.infra.rabbitmq.ConsumerListener;
import br.com.jcsw.math.infra.rabbitmq.ConsumerMessageVerify;
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

    if("Retry".equals(message.toString()) && //
        consumerMessageVerify.verifyMessageConsumeAttempts(Consumer.CONSUMER_DIRECT_TEST.identifier(), message) < //
            Consumer.CONSUMER_DIRECT_TEST.getRetryQty()
        ) {
      throw new RuntimeException("Retry");
    }

    if("DLQ".equals(message.toString())) {
      throw new RuntimeException("DLQ");
    }

    if("Fallback".equals(message.toString())) {
      throw new RuntimeException("Fallback");
    }

    consumerMessageVerify.addMessageConsumed(consumerIdentifier(), message);
  }

  @Override
  public void onMessageFallback(Object message) {

    if("Fallback".equals(message.toString())) {
      consumerMessageVerify.addMessageFallback(consumerIdentifier(), message);
    }

    if("DLQ".equals(message.toString())) {
      throw new RuntimeException("DLQ");
    }
  }

  @Override
  public String consumerIdentifier() {
    return Consumer.CONSUMER_DIRECT_TEST.identifier();
  }
}
