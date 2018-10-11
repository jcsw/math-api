package br.com.jcsw.math.infra;

import static org.mockito.Mockito.when;

import br.com.jcsw.math.infra.api.Address;
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
@SpringBootTest(classes = TestConfiguration.class, webEnvironment = WebEnvironment.MOCK)
public class AddressTest {

  @Autowired
  private Address address;

  @Autowired
  private CorreioHttpClient correioHttpClient;

  @Test
  public void shouldReturnAddressWhenClientItsUp() {

    String zipCode = "76872862";

    String expectedCity = "Ariquemes";

    when(correioHttpClient.searchAddressByZipCode(zipCode)).thenReturn(Optional.of(Map.of("localidade", "Ariquemes")));

    Assert.assertEquals(address.searchAddressByZipCode(zipCode).get("localidade"), expectedCity);
  }

  @Test
  public void shouldReturnEmptyWhenClientItsDown() {

    String zipCode = "01001001";

    when(correioHttpClient.searchAddressByZipCode(zipCode)).thenThrow(new RuntimeException());

    Assert.assertTrue(address.searchAddressByZipCode(zipCode).isEmpty());
  }
}
