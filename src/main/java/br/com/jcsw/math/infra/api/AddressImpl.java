package br.com.jcsw.math.infra.api;

import br.com.jcsw.math.aop.LogExecutionInfo;
import br.com.jcsw.math.infra.correio.CorreioHttpClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressImpl implements Address {

  @Autowired
  private CorreioHttpClient correioHttpClient;

  @HystrixCommand(fallbackMethod = "getCityByZipCodeFallback")
  @LogExecutionInfo
  @Override
  public String getCityByZipCode(String zipCode) {
    return correioHttpClient.addressByZipCode(zipCode).orElseThrow().get("localidade").toString();
  }

  @LogExecutionInfo
  @SuppressWarnings("unused")
  private String getCityByZipCodeFallback(String zipCode) {
    return "São Paulo";
  }

}
