package br.com.jcsw.math.infra.impl;

import br.com.jcsw.math.aop.LogExecutionInfo;
import br.com.jcsw.math.infra.api.FeatureToggle;
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
    return featureToggleHttpClient.isActive("send_to_async_operation");
  }

  @LogExecutionInfo
  @SuppressWarnings("unused")
  public Boolean isActiveSendToAsyncOperationFallback() {
    return Boolean.FALSE;
  }


}
