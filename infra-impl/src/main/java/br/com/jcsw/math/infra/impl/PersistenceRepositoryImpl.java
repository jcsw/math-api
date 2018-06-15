package br.com.jcsw.math.infra.impl;

import br.com.jcsw.math.OperationRequest;
import br.com.jcsw.math.aop.LogExecutionInfo;
import br.com.jcsw.math.infra.api.PersistenceRepository;
import br.com.jcsw.math.mongodb.MathOperationLogEntity;
import br.com.jcsw.math.mongodb.MathOperationRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersistenceRepositoryImpl implements PersistenceRepository {

  @Autowired
  private MathOperationRepository mathOperationRepository;

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
    throw new IllegalStateException("Not implemented");
  }
}