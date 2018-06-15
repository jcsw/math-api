package br.com.jcsw.math;

import br.com.jcsw.math.aop.LogExecutionInfo;
import br.com.jcsw.math.infra.api.FeatureToggle;
import br.com.jcsw.math.infra.api.PersistenceRepository;
import br.com.jcsw.math.infra.api.ProducerMessage;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MathServiceImpl implements MathService {

  @Autowired
  private ProducerMessage producerMessage;

  @Autowired
  private FeatureToggle featureToggle;

  @Autowired
  private PersistenceRepository persistenceRepository;

  @LogExecutionInfo
  @Override
  public OperationResponse executeMathOperation(OperationRequest operationRequest) {

    BigDecimal result = MathOperationExecutor
        .execute(operationRequest.getOperation(), operationRequest.getParameters());
    OperationResponse operationResponse = new OperationResponse(result);

    if(featureToggle.isActiveSendToAsyncOperation()) {
      producerMessage.sendMessageToSendAsyncOperation(operationRequest);
    }

    persistenceRepository.persistMathOperationLog(operationRequest, result);

    return operationResponse;
  }
}
