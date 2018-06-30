package br.com.jcsw.math.chaos;

import org.springframework.stereotype.Component;

@Component
public class InfraComponent {

  @ChaosMonkey
  public String executeAnyOperation() {
    return "OK";
  }

}
