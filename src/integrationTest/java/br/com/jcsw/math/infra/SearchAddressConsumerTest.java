package br.com.jcsw.math.infra;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.jcsw.math.infra.correio.CorreioHttpClient;
import br.com.jcsw.math.infra.mongodb.AddressEntity;
import br.com.jcsw.math.infra.mongodb.AddressRepository;
import java.util.Map;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class, webEnvironment = WebEnvironment.MOCK)
public class SearchAddressConsumerTest {

  @Autowired
  private SearchAddressConsumer searchAddressConsumer;

  @Autowired
  private AddressRepository addressRepository;

  @Autowired
  private CorreioHttpClient correioHttpClient;

  @Test
  public void shouldSaveEntityWhenHasValidZipCode() {

    String zipCode = "76872862";

    when(correioHttpClient.searchAddressByZipCode(zipCode)).thenReturn(Optional.of(Map.of("localidade", "Ariquemes")));

    searchAddressConsumer.onMessage(zipCode);

    verify(addressRepository, times(1)) //
        .insert(any(AddressEntity.class));
  }

}
