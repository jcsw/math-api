package br.com.jcsw.math.chaos;

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
  private InfraComponent infraComponent;

  @Autowired
  private ChaosMonkeyConfiguration chaosMonkeyConfiguration;

  @Test
  public void shouldEnableChaosMonkeyForAnnotationMethod() {

    Map<String, ChaosMonkeySettings> chaosMonkeySettings = chaosMonkeyConfiguration.listChaosMonkeySettings();

    Assert.assertNotNull(chaosMonkeySettings);
    Assert.assertEquals(1, chaosMonkeySettings.size());
    Assert.assertTrue(chaosMonkeySettings.containsKey("executeAnyOperation"));
  }

  @Test
  public void shouldExecuteNormalFlowWhenNotSetSettings() {

    String expectResult = "OK";
    String methodName = "executeAnyOperation";

    chaosMonkeyConfiguration.setEnabled(true);
    chaosMonkeyConfiguration.changeChaosMonkeySettings(methodName, null);

    String result = infraComponent.executeAnyOperation();

    Assert.assertEquals(expectResult, result);
  }

  @Test
  public void shouldExecuteNormalFlowWhenChaosMonkeyIsDisabled() {

    String expectResult = "OK";

    ChaosMonkeySettings chaosMonkeySettings = new ChaosMonkeySettings();
    chaosMonkeySettings.setChaosType(ChaosType.DENIAL);
    String methodName = "executeAnyOperation";

    chaosMonkeyConfiguration.setEnabled(false);
    chaosMonkeyConfiguration.changeChaosMonkeySettings(methodName, chaosMonkeySettings);

    String result = infraComponent.executeAnyOperation();

    Assert.assertEquals(expectResult, result);
  }

  @Test
  public void shouldReturnErrorWhenSettingsDenial() {

    String expectExceptionMessage = "ChaosMonkey";

    ChaosMonkeySettings chaosMonkeySettings = new ChaosMonkeySettings();
    chaosMonkeySettings.setChaosType(ChaosType.DENIAL);
    String methodName = "executeAnyOperation";

    chaosMonkeyConfiguration.setEnabled(true);
    chaosMonkeyConfiguration.changeChaosMonkeySettings(methodName, chaosMonkeySettings);

    try {
      infraComponent.executeAnyOperation();
      Assert.fail();
    } catch (Exception ex) {
      Assert.assertEquals(expectExceptionMessage, ex.getMessage());
    }
  }

}
