package br.com.jcsw.math.infra.impl;

import static org.mockito.Mockito.when;

import br.com.jcsw.math.infra.api.FeatureToggle;
import br.com.jcsw.math.infra.featuretoggle.FeatureToggleHttpClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class FeatureToggleIT {

  @Autowired
  private FeatureToggle featureToggle;

  @MockBean
  private FeatureToggleHttpClient featureToggleHttpClient;

  @Test
  public void shouldReturnTrueWhenClientItsUpAndFeatureItsActive() {

    when(featureToggleHttpClient.isActiveFeature("send_to_async_operation")) //
        .thenReturn(Boolean.TRUE);

    Assert.assertEquals(featureToggle.isActiveSendToAsyncOperation(), Boolean.TRUE);
  }

  @Test
  public void shouldReturnFalseWhenClientItsUpAndFeatureItsInactive() {

    when(featureToggleHttpClient.isActiveFeature("send_to_async_operation")) //
        .thenReturn(Boolean.FALSE);

    Assert.assertEquals(featureToggle.isActiveSendToAsyncOperation(), Boolean.FALSE);
  }

  @Test
  public void shouldReturnFalseValueWhenClientItsDown() {

    when(featureToggleHttpClient.isActiveFeature("send_to_async_operation")) //
        .thenThrow(new RuntimeException());

    Assert.assertEquals(featureToggle.isActiveSendToAsyncOperation(), Boolean.FALSE);
  }
}
