package br.com.jcsw.math.infra.api;

import static br.com.jcsw.math.infra.mongodb.ConsumerListener.ASYNC_MATH_OPERATION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import br.com.jcsw.math.domain.MathOperation;
import br.com.jcsw.math.domain.OperationRequest;
import br.com.jcsw.math.infra.mongodb.RabbitMQAsyncMessageProducer;
import br.com.jcsw.math.mongodb.AsyncMessageFallbackEntity;
import br.com.jcsw.math.mongodb.AsyncMessageFallbackRepository;
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

  @Autowired
  private AsyncMessageFallbackRepository asyncMessageFallbackRepository;

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
  public void shouldExecuteFallbackWhenRabbitMQAsyncMessageProducerReturnError() {

    OperationRequest operationRequest = //
        new OperationRequest(MathOperation.SUM, Arrays.asList(BigDecimal.ONE, BigDecimal.ONE));

    doThrow(new RuntimeException("Connection error")) //
        .when(rabbitMQAsyncMessageProducer).sendMessage(ASYNC_MATH_OPERATION, operationRequest);

    asyncMessageProducer.sendMessageToAsyncMathOperation(operationRequest);

    verify(asyncMessageFallbackRepository, times(1)) //
        .insert(any(AsyncMessageFallbackEntity.class));
  }

}
