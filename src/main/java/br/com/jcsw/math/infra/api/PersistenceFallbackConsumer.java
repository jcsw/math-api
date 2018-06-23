package br.com.jcsw.math.infra.api;

import br.com.jcsw.math.aop.LogExecutionInfo;
import br.com.jcsw.math.infra.mongodb.MathOperationLogEntity;
import br.com.jcsw.math.infra.mongodb.MathOperationRepository;
import br.com.jcsw.math.infra.rabbitmq.ConsumerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersistenceFallbackConsumer implements ConsumerListener {

  private static final Logger logger = LoggerFactory.getLogger(PersistenceFallbackConsumer.class);

  @Autowired
  private MathOperationRepository mathOperationRepository;

  @LogExecutionInfo
  @Override
  public void onMessage(Object message) {

    if(message instanceof MathOperationLogEntity) {

      MathOperationLogEntity entity = (MathOperationLogEntity) message;

      if(mathOperationRepository.findByIdt(entity.getIdt()).isPresent()) {
        logger.info("label=ENTITY_ALREADY_EXIST entity={}", entity);
        return;
      }

      mathOperationRepository.insert(entity);
      logger.info("label=PERSISTING_ENTITY entity={}", entity);

      return;
    }

    throw new RuntimeException(String.format("Invalid entity [%s]", message.getClass()));
  }

  @Override
  public String consumerIdentifier() {
    return PERSISTENCE_FALLBACK;
  }
}
