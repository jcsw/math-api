package br.com.jcsw.math.infra.api;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class MathOperationConsumerIT {

  @Autowired
  private MathOperationConsumer mathOperationConsumer;


  @Test
  public void shouldReturnNotImplementedWhenReceiveMessage() {

    String expectedErrorMessage = "Not implemented";

    try {
      mathOperationConsumer.onMessage(StringUtils.EMPTY);
      Assert.fail();
    } catch (Exception ex) {
      Assert.assertEquals(expectedErrorMessage, ex.getMessage());
    }

  }

}