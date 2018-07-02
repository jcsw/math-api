package br.com.jcsw.math.chaosmonkey;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestControllerEndpoint(id = "chaos-monkey")
class ChaosMonkeyRestEndpoint {

  @Autowired
  private ChaosMonkeyConfiguration chaosMonkeyConfiguration;

  @GetMapping("/settings")
  Map<String, ChaosMonkeySettings> getSettings() {
    return chaosMonkeyConfiguration.listChaosMonkeySettings();
  }

  @PostMapping("/enable")
  ResponseEntity<String> enableChaosMonkey() {
    chaosMonkeyConfiguration.setEnabled(true);
    return ResponseEntity.ok().body("Chaos Monkey is enabled");
  }

  @PostMapping("/disable")
  ResponseEntity<String> disableChaosMonkey() {
    chaosMonkeyConfiguration.setEnabled(false);
    return ResponseEntity.ok().body("Chaos Monkey is disabled");
  }

  @GetMapping("/status")
  ResponseEntity<String> getStatus() {
    if(chaosMonkeyConfiguration.isEnabled()) {
      return ResponseEntity.status(HttpStatus.OK).body("Ready to Cause!");
    } else {
      return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("I am down!");
    }
  }

}
