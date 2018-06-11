package br.com.jcsw.math;

import br.com.jcsw.math.aop.LogExecutionInfo;
import br.com.jcsw.math.infra.ft.FeatureToggleClient;
import br.com.jcsw.math.infra.rabbitmq.Producer;
import br.com.jcsw.math.infra.rabbitmq.ProducerMessage;
import br.com.jcsw.math.mongodb.MathOperationEntity;
import br.com.jcsw.math.mongodb.MathOperationRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MathServiceImpl implements MathService {

  @Autowired
  private ProducerMessage producerMessage;

  @Autowired
  private FeatureToggleClient featureToggleClient;

  @Autowired
  private MathOperationRepository mathOperationRepository;

  @LogExecutionInfo
  @Override
  public OperationResponse executeMathOperation(OperationRequest operationRequest) {

    BigDecimal result = MathOperationExecutor.execute(operationRequest.getOperation(), operationRequest.getParameters());
    OperationResponse operationResponse = new OperationResponse(result);

    if(featureToggleClient.active("send_to_async_operation")) {
      producerMessage.sendMessage(Producer.ASYNC_OPERATION, operationResponse);
    }

    mathOperationRepository.insert(
        new MathOperationEntity(operationRequest.getOperation().name(), operationRequest.getParameters(), result));

    return operationResponse;
  }
}
