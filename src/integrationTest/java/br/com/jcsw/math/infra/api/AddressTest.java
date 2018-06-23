package br.com.jcsw.math.infra.api;

import static org.mockito.Mockito.when;

import br.com.jcsw.math.infra.correio.CorreioHttpClient;
import java.util.Map;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class AddressTest {

  @Autowired
  private Address address;

  @Autowired
  private CorreioHttpClient correioHttpClient;

  @Test
  public void shouldReturnCityWhenClientItsUp() {

    String zipCode = "76872862";

    String expectedCity = "Ariquemes";

    when(correioHttpClient.addressByZipCode(zipCode)).thenReturn(Optional.of(Map.of("localidade", "Ariquemes")));

    Assert.assertEquals(address.getCityByZipCode(zipCode), expectedCity);
  }

  @Test
  public void shouldReturnDefaultCityWhenClientItsDown() {

    String zipCode = "01001001";

    when(correioHttpClient.addressByZipCode(zipCode)).thenThrow(new RuntimeException());

    Assert.assertEquals(address.getCityByZipCode(zipCode), "São Paulo");
  }
}
