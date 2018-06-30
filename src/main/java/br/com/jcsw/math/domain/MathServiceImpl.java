package br.com.jcsw.math.domain;

import br.com.jcsw.math.aop.LogExecutionInfo;
import br.com.jcsw.math.infra.api.AsyncMessageProducer;
import br.com.jcsw.math.infra.api.PersistenceRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MathServiceImpl implements MathService {

  @Autowired
  private AsyncMessageProducer asyncMessageProducer;

  @Autowired
  private PersistenceRepository persistenceRepository;

  @LogExecutionInfo
  @Override
  public OperationResponse executeMathOperation(OperationRequest operationRequest) {

    BigDecimal result = MathOperationExecutor
        .execute(operationRequest.getOperation(), operationRequest.getParameters());
    OperationResponse operationResponse = new OperationResponse(result);

    asyncMessageProducer.sendMessageToAsyncMathOperation(operationRequest);

    persistenceRepository.persistMathOperationLog(operationRequest, result);

    return operationResponse;
  }
}
