package br.com.jcsw.math.infra.featuretoggle;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = TestConfiguration.class)
public class FeatureToggleHttpClientIT {

  @Autowired
  private FeatureToggleHttpClient featureToggleHttpClient;

  @ClassRule
  public static final WireMockClassRule wiremock = new WireMockClassRule(wireMockConfig().port(9801));

  @Test
  public void thenReturnTrueWhenFeatureActive() {

    String myActivateFeature = "myActivateFeature";

    // Stubbing WireMock
    wiremock.stubFor(get(urlEqualTo("/feature/status/" + myActivateFeature))
        .willReturn(aResponse() //
            .withHeader("Content-Type", "application/json") //
            .withStatus(200)
            .withBody("true")));

    Boolean active = featureToggleHttpClient.isActive(myActivateFeature);

    Assert.assertTrue(active);
  }


  @Test
  public void thenReturnTrueWhenFeatureInactive() {

    String myActivateFeature = "myInactivateFeature";

    // Stubbing WireMock
    wiremock.stubFor(get(urlEqualTo("/feature/status/" + myActivateFeature))
        .willReturn(aResponse() //
            .withHeader("Content-Type", "application/json") //
            .withStatus(200)
            .withBody("false")));

    Boolean active = featureToggleHttpClient.isActive(myActivateFeature);

    Assert.assertFalse(active);
  }

  @Test
  public void thenReturnFalseWhenServerError() {

    String myActivate = "myFeature";

    // Stubbing WireMock
    wiremock.stubFor(get(urlEqualTo("/feature/status/" + myActivate))
        .willReturn(aResponse() //
            .withHeader("Content-Type", "application/json") //
            .withStatus(500)));

    Boolean active = featureToggleHttpClient.isActive(myActivate);

    Assert.assertFalse(active);
  }

}
