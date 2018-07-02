package br.com.jcsw.math.chaosmonkey;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Sets up the use of chaos monkey
 */
@Component
class ChaosMonkeyRegister implements InitializingBean {

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

  void changeMethodChaosMonkeySettings(String methodName, ChaosMonkeySettings chaosMonkeySettings) {
    Assert.notNull(methodName, "methodName is required");
    Assert.notNull(chaosMonkeySettings, "chaosMonkeySettings is required");

    chaosMonkeySettings.validate();

    if(monkeySettingsMap.containsKey(methodName)) {
      monkeySettingsMap.put(methodName, chaosMonkeySettings);
    }
  }

  void addMethodToChaosMonkey(String methodName) {
    monkeySettingsMap.put(methodName, ChaosMonkeySettings.makeEmptySettings());
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
