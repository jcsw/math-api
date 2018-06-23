package br.com.jcsw.math.infra.mongodb;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MathOperationRepository extends MongoRepository<MathOperationLogEntity, String> {

  Optional<MathOperationLogEntity> findByIdt(String idt);

}
