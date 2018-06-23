package br.com.jcsw.math.infra.mongodb;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FakeRepository extends MongoRepository<FakeEntity, String> {

  Optional<FakeEntity> findByIdt(String idt);

}
