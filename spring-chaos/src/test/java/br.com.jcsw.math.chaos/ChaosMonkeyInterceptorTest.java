package br.com.jcsw.math.chaos;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class ChaosMonkeyInterceptorTest {

  @Autowired
  private InfraComponent infraComponent;

  @Autowired
  private ChaosMonkeyConfiguration chaosMonkeyConfiguration;

  @Test
  public void shouldExecuteNormalFlow() {

    String expectResult = "OK";

    String result = infraComponent.executeAnyOperation();

    Assert.assertEquals(expectResult, result);
  }

  @Test
  public void shouldExecuteWithDenialError() {

    String expectExceptionMessage = "ChaosMonkey";

    ChaosMonkeySettings chaosMonkeySettings = new ChaosMonkeySettings();
    chaosMonkeySettings.setChaosType(ChaosType.DENIAL);
    String methodName = "executeAnyOperation";

    chaosMonkeyConfiguration.setMonkeySettingsByMethod(methodName, chaosMonkeySettings);

    try {
      infraComponent.executeAnyOperation();
      Assert.fail();
    } catch (Exception ex) {
      Assert.assertEquals(expectExceptionMessage, ex.getMessage());
    }
  }

}
