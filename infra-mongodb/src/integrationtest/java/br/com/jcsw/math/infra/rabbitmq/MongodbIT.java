package br.com.jcsw.math.infra.rabbitmq;

import java.util.Date;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = TestConfiguration.class)
public class MongodbIT {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private CollectionTestRepository collectionTestRepository;

  @Test
  public void insertCollection() {

    String data = "test." + new Date().getTime();

    CollectionTest collectionTest = new CollectionTest(data);
    collectionTestRepository.insert(collectionTest);

    List<CollectionTest> collectionTestList = mongoTemplate.findAll(CollectionTest.class);

    Assert.assertEquals(1, collectionTestList.size());

    Assert.assertEquals(collectionTest, collectionTestList.get(0));
  }

}
