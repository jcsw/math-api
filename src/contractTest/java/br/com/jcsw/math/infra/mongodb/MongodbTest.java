package br.com.jcsw.math.infra.mongodb;

import java.util.Date;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = TestConfiguration.class)
@ActiveProfiles("mongodb")
public class MongodbTest {

  @Autowired
  private FakeRepository fakeRepository;

  @Test
  public void insertCollection() {

    String data = "test-" + new Date().getTime();

    FakeEntity fakeEntity = new FakeEntity(data);
    fakeRepository.insert(fakeEntity);

    FakeEntity fakeEntityFind = fakeRepository.findByIdt(fakeEntity.getIdt()).orElseThrow();

    Assert.assertEquals(fakeEntity, fakeEntityFind);
  }

}
