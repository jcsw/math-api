package br.com.jcsw.math.infra;

import br.com.jcsw.math.aop.LogExecutionInfo;
import br.com.jcsw.math.domain.api.OperationRequest;
import br.com.jcsw.math.infra.api.AsyncMessageProducer;
import br.com.jcsw.math.infra.api.PersistenceRepository;
import br.com.jcsw.math.infra.mongodb.MathOperationLogEntity;
import br.com.jcsw.math.infra.mongodb.MathOperationRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class PersistenceRepositoryImpl implements PersistenceRepository {

  @Autowired
  private MathOperationRepository mathOperationRepository;

  @Autowired
  private AsyncMessageProducer asyncMessageProducer;

  @HystrixCommand(fallbackMethod = "persistMathOperationLogFallback")
  @LogExecutionInfo
  @Override
  public void persistMathOperationLog(OperationRequest operationRequest, BigDecimal result) {
    mathOperationRepository.insert( //
        new MathOperationLogEntity( //
            operationRequest.getOperation().name(), //
            operationRequest.getParameters(), //
            result));
  }

  @LogExecutionInfo
  @SuppressWarnings("unused")
  private void persistMathOperationLogFallback(OperationRequest operationRequest, BigDecimal result) {
    asyncMessageProducer.sendMessageToPersistenceFallback( //
        new MathOperationLogEntity( //
            operationRequest.getOperation().name(), //
            operationRequest.getParameters(), //
            result));
  }
}