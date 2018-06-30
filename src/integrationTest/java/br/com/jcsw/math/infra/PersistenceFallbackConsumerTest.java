package br.com.jcsw.math.infra;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.jcsw.math.infra.mongodb.MathOperationLogEntity;
import br.com.jcsw.math.infra.mongodb.MathOperationRepository;
import java.util.Optional;
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
public class PersistenceFallbackConsumerTest {

  @Autowired
  private PersistenceFallbackConsumer persistenceFallbackConsumer;

  @Autowired
  private MathOperationRepository mathOperationRepository;

  @Test
  public void shouldReturnInvalidEntityWhenMessageIsNotEntity() {

    String expectedErrorMessage = "Invalid entity [class java.lang.String]";

    try {
      persistenceFallbackConsumer.onMessage(StringUtils.EMPTY);
      Assert.fail();
    } catch (Exception ex) {
      Assert.assertEquals(expectedErrorMessage, ex.getMessage());
    }
  }

  @Test
  public void shouldSaveEntityWhenMessageIsMathOperationLogEntityAndEntityIsNotSaved() {

    MathOperationLogEntity entity = new MathOperationLogEntity();

    when(mathOperationRepository.findByIdt(entity.getIdt())).thenReturn(Optional.empty());

    persistenceFallbackConsumer.onMessage(entity);

    verify(mathOperationRepository, times(1)) //
        .insert(any(MathOperationLogEntity.class));

  }

  @Test
  public void shouldNotSaveEntityWhenMessageIsMathOperationLogEntityAndEntityIsSaved() {

    MathOperationLogEntity entity = new MathOperationLogEntity();

    when(mathOperationRepository.findByIdt(entity.getIdt())).thenReturn(Optional.of(entity));

    persistenceFallbackConsumer.onMessage(entity);

    verify(mathOperationRepository, times(0)) //
        .insert(any(MathOperationLogEntity.class));

  }

}
