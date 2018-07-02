package br.com.jcsw.math.infra;

import static br.com.jcsw.math.infra.rabbitmq.RabbitMQAsyncMessageProducer.SEARCH_ADDRESS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import br.com.jcsw.math.infra.api.AsyncMessageProducer;
import br.com.jcsw.math.infra.mongodb.AsyncMessageFallbackEntity;
import br.com.jcsw.math.infra.mongodb.AsyncMessageFallbackRepository;
import br.com.jcsw.math.infra.rabbitmq.RabbitMQAsyncMessageProducer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class AsyncMessageProducerTest {

  @Autowired
  private RabbitMQAsyncMessageProducer rabbitMQAsyncMessageProducer;

  @Autowired
  private AsyncMessageProducer asyncMessageProducer;

  @Autowired
  private AsyncMessageFallbackRepository asyncMessageFallbackRepository;

  @Test
  public void shouldContinueWhenRabbitMQAsyncMessageProducerNotReturnError() {

    String zipCode = "76872862";

    doNothing() //
        .when(rabbitMQAsyncMessageProducer).sendMessage(SEARCH_ADDRESS, zipCode);

    try {
      asyncMessageProducer.sendMessageToSearchAddress(zipCode);
    } catch (Exception ex) {
      Assert.fail(ex.getMessage());
    }
  }

  @Test
  public void shouldExecuteFallbackWhenRabbitMQAsyncMessageProducerReturnError() {

    String zipCode = "01001001";

    doThrow(new RuntimeException("Connection error")) //
        .when(rabbitMQAsyncMessageProducer).sendMessage(SEARCH_ADDRESS, zipCode);

    asyncMessageProducer.sendMessageToSearchAddress(zipCode);

    verify(asyncMessageFallbackRepository, times(1)) //
        .insert(any(AsyncMessageFallbackEntity.class));
  }

}
