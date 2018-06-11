package br.com.jcsw.math;

import br.com.jcsw.math.infra.rabbitmq.Consumer;
import br.com.jcsw.math.infra.rabbitmq.ConsumerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AsyncOperationConsumerListener implements ConsumerListener {

  private static final Logger logger = LoggerFactory.getLogger(AsyncOperationConsumerListener.class);

  @Override
  public void onMessage(Object message) {
    logger.info("method=onMessage message={}", message);
    throw new RuntimeException("error");
  }

  @Override
  public String consumerIdentifier() {
    return Consumer.ASYNC_OPERATION.identifier();
  }
}
