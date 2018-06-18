package br.com.jcsw.math.infra.api;

import br.com.jcsw.math.aop.LogExecutionInfo;
import br.com.jcsw.math.infra.rabbitmq.ConsumerListener;
import org.springframework.stereotype.Component;

@Component
public class MathOperationAsyncConsumer implements ConsumerListener {

  @LogExecutionInfo
  @Override
  public void onMessage(Object message) {
    throw new RuntimeException("Not implemented");
  }

  @Override
  public String consumerIdentifier() {
    return ASYNC_MATH_OPERATION;
  }
}
