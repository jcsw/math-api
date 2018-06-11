package br.com.jcsw.math.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MathOperationRepository extends MongoRepository<MathOperationEntity, String> {

}
