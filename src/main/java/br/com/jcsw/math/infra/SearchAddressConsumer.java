package br.com.jcsw.math.infra;

import br.com.jcsw.math.aop.LogExecutionInfo;
import br.com.jcsw.math.infra.api.Address;
import br.com.jcsw.math.infra.mongodb.AddressEntity;
import br.com.jcsw.math.infra.mongodb.AddressRepository;
import br.com.jcsw.math.infra.rabbitmq.ConsumerListener;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
class SearchAddressConsumer implements ConsumerListener {

  @Autowired
  private Address address;

  @Autowired
  private AddressRepository addressRepository;

  @LogExecutionInfo
  @Override
  public void onMessage(Object zipCode) {
    Assert.notNull(zipCode, "message could not null");
    Map<String, Object> zipCodeAddress = address.searchAddressByZipCode(String.valueOf(zipCode));

    addressRepository.insert(new AddressEntity(String.valueOf(zipCode), zipCodeAddress));
  }

  @Override
  public String consumerIdentifier() {
    return SEARCH_ADDRESS;
  }
}
