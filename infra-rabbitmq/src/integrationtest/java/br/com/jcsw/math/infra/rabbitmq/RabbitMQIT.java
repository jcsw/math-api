package br.com.jcsw.math.infra.rabbitmq;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = TestConfiguration.class)
public class RabbitMQIT {

  @Autowired
  private ProducerMessage producerMessage;

  @Autowired
  private ConsumerMessageVerify consumerMessageVerify;

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Test
  public void consumerMessageOnDirectProducer() {

    //Given
    String message = "directProducer-" + new Date().getTime();

    //When
    producerMessage.sendMessage(Producer.PRODUCER_DIRECT_TEST, message);
    waitConsumeMessage();

    //Then
    assertTrue(consumerMessageVerify.verifyMessageConsumed(Consumer.CONSUMER_DIRECT_TEST.identifier(), message));
    assertEquals(1,
        consumerMessageVerify.verifyMessageConsumeAttempts(Consumer.CONSUMER_DIRECT_TEST.identifier(), message));
  }

  @Test
  public void consumerMessageOnFanoutProducer() {

    //Given
    String message = "fanoutProducer-" + new Date().getTime();

    //When
    producerMessage.sendMessage(Producer.PRODUCER_FANOUT_TEST, message);
    waitConsumeMessage();

    //Then
    assertTrue(consumerMessageVerify.verifyMessageConsumed(Consumer.CONSUMER_FANOUT_TEST_1.identifier(), message));
    assertEquals(1,
        consumerMessageVerify.verifyMessageConsumeAttempts(Consumer.CONSUMER_FANOUT_TEST_1.identifier(), message));

    assertTrue(consumerMessageVerify.verifyMessageConsumed(Consumer.CONSUMER_FANOUT_TEST_2.identifier(), message));
    assertEquals(1,
        consumerMessageVerify.verifyMessageConsumeAttempts(Consumer.CONSUMER_FANOUT_TEST_2.identifier(), message));

  }

  @Test
  public void retryMessageConsumer() {

    //Given
    String message = "Retry";

    //When
    producerMessage.sendMessage(Producer.PRODUCER_DIRECT_TEST, message);
    waitConsumeMessage();

    //Then
    assertTrue(consumerMessageVerify.verifyMessageConsumed(Consumer.CONSUMER_DIRECT_TEST.identifier(), message));
    assertEquals(Consumer.CONSUMER_DIRECT_TEST.getRetryQty(),
        consumerMessageVerify.verifyMessageConsumeAttempts(Consumer.CONSUMER_DIRECT_TEST.identifier(), message));

  }

  @Test
  public void fallbackMessageConsumer() {

    //Given
    String message = "Fallback";

    //When
    producerMessage.sendMessage(Producer.PRODUCER_DIRECT_TEST, message);
    waitConsumeMessage();

    //Then
    assertFalse(consumerMessageVerify.verifyMessageConsumed(Consumer.CONSUMER_DIRECT_TEST.identifier(), message));
    assertEquals(Consumer.CONSUMER_DIRECT_TEST.getRetryQty() + 1,
        consumerMessageVerify.verifyMessageConsumeAttempts(Consumer.CONSUMER_DIRECT_TEST.identifier(), message));
    assertTrue(consumerMessageVerify.verifyMessageFallback(Consumer.CONSUMER_DIRECT_TEST.identifier(), message));
  }

  @Test
  public void sendToDLQ() {

    //Given
    String message = "DLQ";

    //When
    producerMessage.sendMessage(Producer.PRODUCER_DIRECT_TEST, message);
    waitConsumeMessage();

    //Then
    assertFalse(consumerMessageVerify.verifyMessageConsumed(Consumer.CONSUMER_DIRECT_TEST.identifier(), message));
    assertEquals(Consumer.CONSUMER_DIRECT_TEST.getRetryQty() + 1,
        consumerMessageVerify.verifyMessageConsumeAttempts(Consumer.CONSUMER_DIRECT_TEST.identifier(), message));

    Object dlqMessage = rabbitTemplate.receiveAndConvert(Consumer.CONSUMER_DIRECT_TEST.dlqIdentifier());
    assertEquals(message, dlqMessage);

  }

  private void waitConsumeMessage() {
    try {
      Thread.sleep(200);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
