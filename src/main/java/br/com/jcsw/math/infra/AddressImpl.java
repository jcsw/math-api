package br.com.jcsw.math.infra;

import br.com.jcsw.math.aop.LogExecutionInfo;
import br.com.jcsw.math.chaosmonkey.ChaosMonkey;
import br.com.jcsw.math.infra.api.Address;
import br.com.jcsw.math.infra.correio.CorreioHttpClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class AddressImpl implements Address {

  @Autowired
  private CorreioHttpClient correioHttpClient;

  @HystrixCommand(fallbackMethod = "searchAddressByZipCodeFallback")
  @ChaosMonkey
  @LogExecutionInfo
  @Override
  public Map<String, Object> searchAddressByZipCode(String zipCode) {
    return correioHttpClient.searchAddressByZipCode(zipCode).orElseThrow();
  }

  @LogExecutionInfo
  @SuppressWarnings("unused")
  private Map<String, Object> searchAddressByZipCodeFallback(String zipCode) {
    return new HashMap<>();
  }

}
