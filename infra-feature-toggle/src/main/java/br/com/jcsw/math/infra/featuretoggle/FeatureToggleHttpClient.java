package br.com.jcsw.math.infra.featuretoggle;


import br.com.jcsw.math.infra.featuretoggle.FeatureToggleHttpClient.FeatureToggleHttpClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "feature-toggle", fallback = FeatureToggleHttpClientFallback.class)
public interface FeatureToggleHttpClient {

  @RequestMapping(path = "/feature/status/{feature_name}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
  Boolean isActive(@PathVariable("feature_name") String featureName);

  @Component
  class FeatureToggleHttpClientFallback implements FeatureToggleHttpClient {

    @Override
    public Boolean isActive(String featureName) {
      return Boolean.FALSE;
    }
  }

}
