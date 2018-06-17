package br.com.jcsw.math.infra.api;

import br.com.jcsw.math.aop.LogExecutionInfo;
import br.com.jcsw.math.infra.featuretoggle.FeatureToggleHttpClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FeatureToggleImpl implements FeatureToggle {

  @Autowired
  private FeatureToggleHttpClient featureToggleHttpClient;

  @HystrixCommand(fallbackMethod = "isActiveSendToAsyncOperationFallback")
  @LogExecutionInfo
  @Override
  public Boolean isActiveSendToAsyncOperation() {
    return featureToggleHttpClient.isActiveFeature("send_to_async_math_operation");
  }

  @LogExecutionInfo
  @SuppressWarnings("unused")
  private Boolean isActiveSendToAsyncOperationFallback() {
    return Boolean.FALSE;
  }


}
