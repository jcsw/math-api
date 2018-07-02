package br.com.jcsw.math.chaosmonkey;

import org.springframework.stereotype.Component;

@Component
class FakeComponent {

  @ChaosMonkey
  String executeAnyOperation() {
    return "OK";
  }

}
