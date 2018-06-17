package br.com.jcsw.math.infra.impl;

import static br.com.jcsw.math.infra.mongodb.RabbitMQAsyncMessageProducer.ASYNC_MATH_OPERATION;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import br.com.jcsw.math.MathOperation;
import br.com.jcsw.math.OperationRequest;
import br.com.jcsw.math.infra.api.AsyncMessageProducer;
import br.com.jcsw.math.infra.mongodb.RabbitMQAsyncMessageProducer;
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
public class AsyncMessageProducerIT {

  @Autowired
  private RabbitMQAsyncMessageProducer rabbitMQAsyncMessageProducer;

  @Autowired
  private AsyncMessageProducer asyncMessageProducer;

  @Test
  public void shouldContinueWhenRabbitMQAsyncMessageProducerNotReturnError() {

    OperationRequest operationRequest = //
        new OperationRequest(MathOperation.SUM, Arrays.asList(BigDecimal.ONE, BigDecimal.ONE));

    doNothing() //
        .when(rabbitMQAsyncMessageProducer).sendMessage(ASYNC_MATH_OPERATION, operationRequest);

    try {
      asyncMessageProducer.sendMessageToAsyncMathOperation(operationRequest);
    } catch (Exception ex) {
      Assert.fail(ex.getMessage());
    }
  }

  @Test
  public void shouldReturnErrorWhenRabbitMQAsyncMessageProducerReturnError() {

    OperationRequest operationRequest = //
        new OperationRequest(MathOperation.SUM, Arrays.asList(BigDecimal.ONE, BigDecimal.ONE));

    doThrow(new RuntimeException("Connection error")) //
        .when(rabbitMQAsyncMessageProducer).sendMessage(ASYNC_MATH_OPERATION, operationRequest);

    try {
      asyncMessageProducer.sendMessageToAsyncMathOperation(operationRequest);
      Assert.fail();
    } catch (Exception ex) {
      Assert.assertEquals("Fallback not implemented", ex.getMessage());
    }
  }

}
