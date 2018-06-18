package br.com.jcsw.math.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AsyncMessageFallbackRepository extends MongoRepository<AsyncMessageFallbackEntity, String> {

}
