package br.com.jcsw.math.infra.correio;

import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = TestConfiguration.class)
@ActiveProfiles("correio")
public class CorreioHttpClientTest {

  @Autowired
  private CorreioHttpClient correioHttpClient;

  @Test
  public void thenReturnAddress() {

    String validZipCode = "01001000";

    Map<String, Object> address = correioHttpClient.addressByZipCode(validZipCode).orElseThrow();

    Assert.assertNotNull(address);
    Assert.assertEquals("São Paulo", address.get("localidade"));
  }

}
