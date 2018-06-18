package br.com.jcsw.math.infra.rabbitmq;

import static br.com.jcsw.math.infra.rabbitmq.RabbitMQArguments.CONSUMER_PREFIX;

import br.com.jcsw.math.aop.LogExecutionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsumerDlqTestListener implements ConsumerListener {

  @Autowired
  private ConsumerMessageVerify consumerMessageVerify;

  @LogExecutionInfo
  @Override
  public void onMessage(Object message) {
    consumerMessageVerify.addMessageConsumeAttempt(consumerIdentifier(), message);
    throw new RuntimeException("dlq");
  }

  @Override
  public String consumerIdentifier() {
    return CONSUMER_PREFIX + "dlq_test";
  }
}
