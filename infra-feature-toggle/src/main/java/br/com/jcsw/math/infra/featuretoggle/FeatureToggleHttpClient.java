package br.com.jcsw.math.infra.featuretoggle;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "feature-toggle")
public interface FeatureToggleHttpClient {

  @RequestMapping(path = "/feature/status/{feature_name}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
  Boolean isActive(@PathVariable("feature_name") String featureName);

}
