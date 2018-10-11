package br.com.jcsw.math.infra;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.jcsw.math.domain.api.MathOperation;
import br.com.jcsw.math.domain.api.OperationRequest;
import br.com.jcsw.math.infra.api.AsyncMessageProducer;
import br.com.jcsw.math.infra.api.PersistenceRepository;
import br.com.jcsw.math.infra.mongodb.MathOperationLogEntity;
import br.com.jcsw.math.infra.mongodb.MathOperationRepository;
import java.math.BigDecimal;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class, webEnvironment = WebEnvironment.MOCK)
public class PersistenceRepositoryTest {

  @Autowired
  private MathOperationRepository mathOperationRepository;

  @Autowired
  private PersistenceRepository persistenceRepository;

  @MockBean
  private AsyncMessageProducer asyncMessageProducer;

  @Test
  public void shouldContinueWhenMathOperationRepositoryNotReturnError() {

    OperationRequest operationRequest = new OperationRequest();
    operationRequest.setOperation(MathOperation.SUM);
    operationRequest.setParameters(Arrays.asList(BigDecimal.TEN, BigDecimal.ZERO));
    BigDecimal result = BigDecimal.TEN;

    MathOperationLogEntity entity = new MathOperationLogEntity();
    when(mathOperationRepository.insert(entity)).thenReturn(entity);

    try {
      persistenceRepository.persistMathOperationLog(operationRequest, result);
    } catch (Exception ex) {
      Assert.fail(ex.getMessage());
    }

  }

  @Test
  public void shouldExecuteFallbackWhenMathOperationRepositoryReturnError() {

    OperationRequest operationRequest = new OperationRequest();
    operationRequest.setOperation(MathOperation.SUM);
    operationRequest.setParameters(Arrays.asList(BigDecimal.TEN, BigDecimal.ZERO));
    BigDecimal result = BigDecimal.TEN;

    when(mathOperationRepository.insert(any(MathOperationLogEntity.class)))
        .thenThrow(new RuntimeException("Connection error"));

    persistenceRepository.persistMathOperationLog(operationRequest, result);

    verify(asyncMessageProducer, times(1)) //
        .sendMessageToPersistenceFallback(any(MathOperationLogEntity.class));
  }

}
