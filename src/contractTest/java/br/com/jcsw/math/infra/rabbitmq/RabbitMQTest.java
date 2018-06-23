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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = TestConfiguration.class)
@ActiveProfiles("rabbitmq")
public class RabbitMQTest {

  @Autowired
  private RabbitMQAsyncMessageProducer producerMessage;

  @Autowired
  private ConsumerMessageVerify consumerMessageVerify;

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Test
  public void consumerMessageOnDirectProducer() {

    //Given
    Producer producerDirect = new Producer();
    producerDirect.setName("direct_test");
    producerDirect.setType(ProducerType.DIRECT);

    Consumer consumer = new Consumer();
    consumer.setName("direct_test");
    consumer.setProducer("direct_test");
    consumer.setRetryQty(2);
    consumer.setRetryTime(100);

    MessageVO messageVO = new MessageVO("direct-producer", new Date());

    //When
    producerMessage.sendMessage(producerDirect.identifier(), messageVO);
    waitConsumeMessage();

    //Then
    long expectedAttempts = 1;

    assertTrue(consumerMessageVerify.verifyMessageConsumed(consumer.identifier(), messageVO));
    assertEquals(expectedAttempts, consumerMessageVerify.countMessageConsumeAttempts(consumer.identifier(), messageVO));
  }

  @Test
  public void consumerMessageOnFanoutProducer() {

    //Given
    Producer producerFanout = new Producer();
    producerFanout.setName("fanout_test");
    producerFanout.setType(ProducerType.FANOUT);

    Consumer consumer1 = new Consumer();
    consumer1.setName("fanout_test_1");
    consumer1.setProducer("fanout_test");
    consumer1.setRetryQty(2);
    consumer1.setRetryTime(100);

    Consumer consumer2 = new Consumer();
    consumer2.setName("fanout_test_2");
    consumer2.setProducer("fanout_test");
    consumer2.setRetryQty(2);
    consumer2.setRetryTime(100);

    MessageVO messageVO = new MessageVO("fanout-producer", new Date());

    //When
    producerMessage.sendMessage(producerFanout.identifier(), messageVO);
    waitConsumeMessage();

    //Then
    long expectedAttempts = 1;

    assertTrue(consumerMessageVerify.verifyMessageConsumed(consumer1.identifier(), messageVO));
    assertEquals(expectedAttempts,
        consumerMessageVerify.countMessageConsumeAttempts(consumer1.identifier(), messageVO));

    assertTrue(consumerMessageVerify.verifyMessageConsumed(consumer2.identifier(), messageVO));
    assertEquals(expectedAttempts,
        consumerMessageVerify.countMessageConsumeAttempts(consumer2.identifier(), messageVO));

  }

  @Test
  public void retryMessageConsumer() {

    //Given
    Producer producer = new Producer();
    producer.setName("retry_test");
    producer.setType(ProducerType.DIRECT);

    Consumer consumer = new Consumer();
    consumer.setName("retry_test");
    consumer.setProducer("retry_test");
    consumer.setRetryQty(3);
    consumer.setRetryTime(10);

    String message = "retry" + new Date().getTime();

    //When
    producerMessage.sendMessage(producer.identifier(), message);
    waitConsumeMessage();

    //Then
    long expectedAttempts = 3;

    assertTrue(consumerMessageVerify.verifyMessageConsumed(consumer.identifier(), message));
    assertEquals(expectedAttempts, consumerMessageVerify.countMessageConsumeAttempts(consumer.identifier(), message));
  }

  @Test
  public void fallbackMessageConsumer() {

    //Given
    Producer producer = new Producer();
    producer.setName("fallback_test");
    producer.setType(ProducerType.DIRECT);

    Consumer consumer = new Consumer();
    consumer.setName("fallback_test");
    consumer.setProducer("fallback_test");
    consumer.setRetryQty(3);
    consumer.setRetryTime(10);

    MessageVO messageVO = new MessageVO("fallback", new Date());

    //When
    producerMessage.sendMessage(producer.identifier(), messageVO);
    waitConsumeMessage();

    //Then
    long expectedAttempts = 4;

    assertFalse(consumerMessageVerify.verifyMessageConsumed(consumer.identifier(), messageVO));
    assertEquals(expectedAttempts, consumerMessageVerify.countMessageConsumeAttempts(consumer.identifier(), messageVO));
    assertTrue(consumerMessageVerify.verifyMessageFallback(consumer.identifier(), messageVO));
  }

  @Test
  public void sendToDLQ() {

    //Given
    Producer producer = new Producer();
    producer.setName("dlq_test");
    producer.setType(ProducerType.DIRECT);

    Consumer consumer = new Consumer();
    consumer.setName("dlq_test");
    consumer.setProducer("dlq_test");
    consumer.setRetryQty(3);
    consumer.setRetryTime(10);

    MessageVO messageVO = new MessageVO("dlq", new Date());

    //When
    producerMessage.sendMessage(producer.identifier(), messageVO);
    waitConsumeMessage();

    //Then
    long expectedAttempts = 4;

    assertFalse(consumerMessageVerify.verifyMessageConsumed(consumer.identifier(), messageVO));
    assertEquals(expectedAttempts, consumerMessageVerify.countMessageConsumeAttempts(consumer.identifier(), messageVO));

    Object dlqMessage = rabbitTemplate.receiveAndConvert(consumer.dlqIdentifier());
    assertEquals(messageVO, dlqMessage);

  }

  private void waitConsumeMessage() {
    try {
      Thread.sleep(200);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
