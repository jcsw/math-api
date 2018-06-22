package br.com.jcsw.math.infra.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FakeRepository extends MongoRepository<FakeEntity, String> {

  FakeEntity findByIdt(String idt);

}
