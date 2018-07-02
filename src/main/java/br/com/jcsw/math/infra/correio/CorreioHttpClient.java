package br.com.jcsw.math.infra.correio;


import java.util.Map;
import java.util.Optional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "correio")
public interface CorreioHttpClient {

  @RequestMapping(path = "/ws/{zipCode}/json/", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
  Optional<Map<String, Object>> searchAddressByZipCode(@PathVariable("zipCode") String zipCode);

}
