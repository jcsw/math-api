package br.com.jcsw.math.chaosmonkey;

import java.util.Date;
import java.util.Map;
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
  private FakeComponent fakeComponent;

  @Autowired
  private ChaosMonkeyRegister chaosMonkeyRegister;

  @Test
  public void shouldEnableChaosMonkeyForAnnotationMethod() {

    Map<String, ChaosMonkeySettings> chaosMonkeySettings = chaosMonkeyRegister.listChaosMonkeySettings();

    Assert.assertNotNull(chaosMonkeySettings);
    Assert.assertEquals(1, chaosMonkeySettings.size());
    Assert.assertTrue(chaosMonkeySettings.containsKey("executeAnyOperation"));
  }

  @Test
  public void shouldExecuteNormalFlowWhenNotSetSettings() {

    String expectResult = "OK";
    String methodName = "executeAnyOperation";

    chaosMonkeyRegister.setEnabled(true);
    chaosMonkeyRegister.changeMethodChaosMonkeySettings(methodName, ChaosMonkeySettings.makeEmptySettings());

    String result = fakeComponent.executeAnyOperation();

    Assert.assertEquals(expectResult, result);
  }

  @Test
  public void shouldExecuteNormalFlowWhenChaosMonkeyIsDisabled() {

    String expectResult = "OK";

    ChaosMonkeySettings chaosMonkeySettings = new ChaosMonkeySettings(ChaosMonkeyType.DENIAL);
    String methodName = "executeAnyOperation";

    chaosMonkeyRegister.setEnabled(false);
    chaosMonkeyRegister.changeMethodChaosMonkeySettings(methodName, chaosMonkeySettings);

    String result = fakeComponent.executeAnyOperation();

    Assert.assertEquals(expectResult, result);
  }

  @Test
  public void shouldReturnErrorWhenSettingsDenial() {

    String expectExceptionMessage = "Chaos Monkey";

    ChaosMonkeySettings chaosMonkeySettings = new ChaosMonkeySettings(ChaosMonkeyType.DENIAL);
    String methodName = "executeAnyOperation";

    chaosMonkeyRegister.setEnabled(true);
    chaosMonkeyRegister.changeMethodChaosMonkeySettings(methodName, chaosMonkeySettings);

    try {
      fakeComponent.executeAnyOperation();
      Assert.fail();
    } catch (Exception ex) {
      Assert.assertEquals(expectExceptionMessage, ex.getMessage());
    }
  }

  @Test
  public void shouldWaitingTimeWhenSettingsLatency() {

    long timeToExecuteOriginalMethod = 20;

    ChaosMonkeySettings chaosMonkeySettings = new ChaosMonkeySettings(ChaosMonkeyType.LATENCY, 500, 1000);
    String methodName = "executeAnyOperation";

    chaosMonkeyRegister.setEnabled(true);
    chaosMonkeyRegister.changeMethodChaosMonkeySettings(methodName, chaosMonkeySettings);

    Date start = new Date();
    fakeComponent.executeAnyOperation();
    Date end = new Date();

    long time = end.getTime() - start.getTime() - timeToExecuteOriginalMethod;

    Assert.assertTrue(time >= chaosMonkeySettings.getLatencyRangeStart());
    Assert.assertTrue(time <= chaosMonkeySettings.getLatencyRangeEnd());

  }

  @Test
  public void shouldWaitingTimeAndReturnErrorWhenSettingsLatencyAndDenial() {

    long timeToExecuteOriginalMethod = 20;

    String expectExceptionMessage = "Chaos Monkey";

    ChaosMonkeySettings chaosMonkeySettings = new ChaosMonkeySettings(ChaosMonkeyType.LATENCY_AND_DENIAL, 300, 600);
    String methodName = "executeAnyOperation";

    chaosMonkeyRegister.setEnabled(true);
    chaosMonkeyRegister.changeMethodChaosMonkeySettings(methodName, chaosMonkeySettings);

    Date start = new Date();
    try {
      fakeComponent.executeAnyOperation();
      Assert.fail();
    } catch (Exception ex) {

      Date end = new Date();
      long time = end.getTime() - start.getTime() - timeToExecuteOriginalMethod;

      Assert.assertTrue(time >= chaosMonkeySettings.getLatencyRangeStart());
      Assert.assertTrue(time <= chaosMonkeySettings.getLatencyRangeEnd());

      Assert.assertEquals(expectExceptionMessage, ex.getMessage());
    }
  }

}
