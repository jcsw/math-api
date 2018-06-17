package br.com.jcsw.math.infra.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CollectionTestRepository extends MongoRepository<CollectionTest, String> {

}
