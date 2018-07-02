package br.com.jcsw.math.chaosmonkey;

import org.apache.commons.lang3.RandomUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ChaosMonkeyInterceptor {

  private static final Logger logger = LoggerFactory.getLogger(ChaosMonkeyInterceptor.class);

  @Autowired
  private ChaosMonkeyRegister chaosMonkeyRegister;

  @Around("@annotation(ChaosMonkey)")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

    processChaosMonkey(MethodSignature.class.cast(joinPoint.getSignature()));

    return joinPoint.proceed();

  }

  private void processChaosMonkey(MethodSignature methodSignature) {

    if(!chaosMonkeyRegister.isEnabled()) {
      return;
    }

    String methodName = methodSignature.getMethod().getName();

    ChaosMonkeySettings chaosMonkeySettings = chaosMonkeyRegister.getMonkeySettingsByMethod(methodName);

    if(chaosMonkeySettings != null) {

      logger.info("method=processChaosMonkey key={} settings={}", methodName, chaosMonkeySettings);

      switch (chaosMonkeySettings.getChaosMonkeyType()) {
        case LATENCY:
          simulateLatency(chaosMonkeySettings.getLatencyRangeStart(), chaosMonkeySettings.getLatencyRangeEnd());
          break;
        case DENIAL:
          generateChaosException();
          break;
        case LATENCY_AND_DENIAL:
          simulateLatency(chaosMonkeySettings.getLatencyRangeStart(), chaosMonkeySettings.getLatencyRangeEnd());
          generateChaosException();
          break;
      }
    }
  }

  /***
   * Generates a chaosmonkey exception
   */
  private void generateChaosException() {
    logger.info("method=generateChaosException");

    throw new RuntimeException("Chaos Monkey");
  }

  /***
   * Generates a latency
   */
  private void simulateLatency(int rangeStart, int rangeEnd) {

    int latency = RandomUtils.nextInt(rangeStart, rangeEnd);
    logger.info("method=simulateLatency latency={}", latency);

    try {
      Thread.sleep(latency);
    } catch (InterruptedException e) {
      // do nothing
    }
  }

}
