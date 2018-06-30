package br.com.jcsw.math.chaos;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class ChaosMonkeyConfiguration implements InitializingBean {

  private Map<String, ChaosMonkeySettings> monkeySettingsMap;

  @Override
  public void afterPropertiesSet() {
    if(monkeySettingsMap == null) {
      monkeySettingsMap = new HashMap<>();
    }
  }

  public ChaosMonkeySettings getMonkeySettingsByMethod(String methodName) {
    return monkeySettingsMap.getOrDefault(methodName, null);
  }

  public void setMonkeySettingsByMethod(String methodName, ChaosMonkeySettings chaosMonkeySettings) {
    monkeySettingsMap.put(methodName, chaosMonkeySettings);
  }

}
