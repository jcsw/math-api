package br.com.jcsw.math.infra.api;

import br.com.jcsw.math.aop.LogExecutionInfo;
import br.com.jcsw.math.infra.mongodb.MathOperationLogEntity;
import br.com.jcsw.math.infra.mongodb.MathOperationRepository;
import br.com.jcsw.math.infra.rabbitmq.ConsumerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersistenceFallbackConsumer implements ConsumerListener {

  @Autowired
  private MathOperationRepository mathOperationRepository;

  @LogExecutionInfo
  @Override
  public void onMessage(Object message) {

    if(message instanceof MathOperationLogEntity) {
      mathOperationRepository.insert((MathOperationLogEntity) message);
      return;
    }

    throw new RuntimeException("Invalid entity");
  }

  @Override
  public String consumerIdentifier() {
    return PERSISTENCE_FALLBACK;
  }
}
