package br.com.jcsw.math.infra.api;

import static org.mockito.Mockito.when;

import br.com.jcsw.math.domain.MathOperation;
import br.com.jcsw.math.domain.OperationRequest;
import br.com.jcsw.math.mongodb.MathOperationLogEntity;
import br.com.jcsw.math.mongodb.MathOperationRepository;
import java.math.BigDecimal;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class PersistenceRepositoryIT {

  @Autowired
  private MathOperationRepository mathOperationRepository;

  @Autowired
  private PersistenceRepository persistenceRepository;

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
  public void shouldReturnErrorWhenMathOperationRepositoryReturnError() {

    OperationRequest operationRequest = new OperationRequest();
    operationRequest.setOperation(MathOperation.SUM);
    operationRequest.setParameters(Arrays.asList(BigDecimal.TEN, BigDecimal.ZERO));
    BigDecimal result = BigDecimal.TEN;

    when(mathOperationRepository.insert(new MathOperationLogEntity()))
        .thenThrow(new RuntimeException("Connection error"));

    try {
      persistenceRepository.persistMathOperationLog(operationRequest, result);
      Assert.fail();
    } catch (Exception ex) {
      Assert.assertEquals("Fallback not implemented", ex.getMessage());
    }

  }

}
