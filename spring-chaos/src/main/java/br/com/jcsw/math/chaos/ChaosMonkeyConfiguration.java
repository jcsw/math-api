package br.com.jcsw.math.chaos;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * Sets up the use of chaos monkey
 */
@Component
public class ChaosMonkeyConfiguration implements InitializingBean {

  private Map<String, ChaosMonkeySettings> monkeySettingsMap;

  private boolean enabled = false;

  @Override
  public void afterPropertiesSet() {
    if(monkeySettingsMap == null) {
      monkeySettingsMap = new HashMap<>();
    }
  }

  Map<String, ChaosMonkeySettings> listChaosMonkeySettings() {
    return new HashMap<>(monkeySettingsMap);
  }

  void changeChaosMonkeySettings(String methodName, ChaosMonkeySettings chaosMonkeySettings) {
    if(monkeySettingsMap.containsKey(methodName)) {
      monkeySettingsMap.put(methodName, chaosMonkeySettings);
    }
  }

  void enableMethodToChaosMonkey(String methodName) {
    monkeySettingsMap.put(methodName, null);
  }

  ChaosMonkeySettings getMonkeySettingsByMethod(String methodName) {
    return monkeySettingsMap.getOrDefault(methodName, null);
  }

  boolean isEnabled() {
    return enabled;
  }

  void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
}
