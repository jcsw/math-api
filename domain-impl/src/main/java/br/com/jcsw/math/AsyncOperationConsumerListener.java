package br.com.jcsw.math;

import br.com.jcsw.math.aop.LogExecutionInfo;
import br.com.jcsw.math.infra.rabbitmq.ConsumerListener;
import org.springframework.stereotype.Component;

@Component
public class AsyncOperationConsumerListener implements ConsumerListener {

  @LogExecutionInfo
  @Override
  public void onMessage(Object message) {
    throw new RuntimeException("not implemented");
  }

  @Override
  public String consumerIdentifier() {
    return ASYNC_OPERATION;
  }
}
