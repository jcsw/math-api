package br.com.jcsw.math.infra.ft;


import br.com.jcsw.math.infra.ft.FeatureToggleClient.FeatureToggleClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "feature-toggle", fallback = FeatureToggleClientFallback.class)
public interface FeatureToggleClient {

  @RequestMapping(path = "/feature/status/{feature_name}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
  Boolean active(@PathVariable("feature_name") String featureName);

  @Component
  class FeatureToggleClientFallback implements FeatureToggleClient {

    @Override
    public Boolean active(String featureName) {
      return Boolean.FALSE;
    }
  }

}
