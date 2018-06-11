package br.com.jcsw.math.infra.rabbitmq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class ConsumerMessageVerify {

  private static Map<String, Object> messagesConsumed = new HashMap<>();

  private static Map<String, Object> messagesFallback = new HashMap<>();

  private static List<String> messagesConsumeAttempt = new ArrayList<>();

  public void addMessageConsumeAttempt(String consumer, Object message) {
    messagesConsumeAttempt.add(consumer + message);
  }

  public long verifyMessageConsumeAttempts(String consumer, Object message) {
    return messagesConsumeAttempt.stream().filter(s -> s.equals(consumer + message)).count();
  }

  public void addMessageConsumed(String consumer, Object message) {
    messagesConsumed.put(consumer, message);
  }

  boolean verifyMessageConsumed(String consumer, Object message) {
    return messagesConsumed.getOrDefault(consumer, StringUtils.EMPTY).equals(message);
  }

  public void addMessageFallback(String consumer, Object message) {
    messagesFallback.put(consumer, message);
  }

  boolean verifyMessageFallback(String consumer, Object message) {
    return messagesFallback.getOrDefault(consumer, StringUtils.EMPTY).equals(message);
  }

}
