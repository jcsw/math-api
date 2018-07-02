package br.com.jcsw.math.infra.api;

import java.util.Map;

public interface Address {

  Map<String, Object> searchAddressByZipCode(String zipCode);

}
