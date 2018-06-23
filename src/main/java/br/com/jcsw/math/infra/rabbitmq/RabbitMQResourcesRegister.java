package br.com.jcsw.math.infra.rabbitmq;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.util.CharsetUtil;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jalmeida
 *
 * Register RabbitMQ resources
 */
class RabbitMQResourcesRegister {

  private static final Logger logger = LoggerFactory.getLogger(RabbitMQResourcesRegister.class);

  private static final String RABBITMQ_RESOURCE_JSON = "rabbitmq.json";

  private static Map<String, Producer> registeredProducers;

  private static Map<String, Consumer> registeredConsumers;

  static {

    RabbitMQResources rabbitMQResources = makeRabbitMQResources();

    registeredProducers = rabbitMQResources.getProducers().stream()
        .collect(Collectors.toMap(Producer::identifier, p -> p));

    registeredConsumers = rabbitMQResources.getConsumers().stream()
        .collect(Collectors.toMap(Consumer::identifier, c -> c));

    logger.info("registeredProducers > {}", registeredProducers);
    logger.info("registeredConsumers > {}", registeredConsumers);
  }

  private static RabbitMQResources makeRabbitMQResources() {
    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);

      ClassLoader classLoader = ClassLoader.getSystemClassLoader();

      try (InputStreamReader reader = //
          new InputStreamReader(classLoader.getResourceAsStream(RABBITMQ_RESOURCE_JSON), CharsetUtil.UTF_8);
          BufferedReader buffered = new BufferedReader(reader)) {
        String content = buffered.lines().collect(Collectors.joining("\n"));
        return mapper.readValue(content, RabbitMQResources.class);
      }

    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  static Map<String, Producer> getRegisteredProducers() {
    return registeredProducers;
  }

  static Map<String, Consumer> getRegisteredConsumers() {
    return registeredConsumers;
  }

}
