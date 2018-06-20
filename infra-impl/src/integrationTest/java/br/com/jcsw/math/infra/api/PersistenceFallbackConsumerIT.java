package br.com.jcsw.math.infra.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import br.com.jcsw.math.infra.mongodb.MathOperationLogEntity;
import br.com.jcsw.math.infra.mongodb.MathOperationRepository;
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
public class PersistenceFallbackConsumerIT {

  @Autowired
  private PersistenceFallbackConsumer persistenceFallbackConsumer;

  @Autowired
  private MathOperationRepository mathOperationRepository;

  @Test
  public void shouldReturnInvalidEntityWhenMessageIsNotEntity() {

    String expectedErrorMessage = "Invalid entity";

    try {
      persistenceFallbackConsumer.onMessage(StringUtils.EMPTY);
      Assert.fail();
    } catch (Exception ex) {
      Assert.assertEquals(expectedErrorMessage, ex.getMessage());
    }
  }

  @Test
  public void shouldSaveEntityWhenMessageIsMathOperationLogEntity() {

    persistenceFallbackConsumer.onMessage(new MathOperationLogEntity());

    verify(mathOperationRepository, times(1)) //
        .insert(any(MathOperationLogEntity.class));

  }

}
