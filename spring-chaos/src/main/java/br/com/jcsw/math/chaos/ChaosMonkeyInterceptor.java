package br.com.jcsw.math.chaos;

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
  private ChaosMonkeyConfiguration chaosMonkeyConfiguration;

  @Around("@annotation(ChaosMonkey)")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

    processChaosMonkey(MethodSignature.class.cast(joinPoint.getSignature()));

    return joinPoint.proceed();

  }

  private void processChaosMonkey(MethodSignature methodSignature) {

    ChaosMonkeySettings chaosMonkeySettings =
        chaosMonkeyConfiguration.getMonkeySettingsByMethod(methodSignature.getMethod().getName());

    if(chaosMonkeySettings != null) {
      logger.info("method={} key={} settings={}",
          "processChaosMonkey",
          methodSignature.getMethod().getName(),
          chaosMonkeySettings);

      switch (chaosMonkeySettings.getChaosType()) {
        case LATENCY:
          generateLatency(chaosMonkeySettings.getLatencyRangeStart(), chaosMonkeySettings.getLatencyRangeEnd());
        case DENIAL:
          throw new RuntimeException("ChaosMonkey");
      }

    }
  }

  /***
   * Generates a timeout exception
   */
  private void generateLatency(int rangeStart, int rangeEnd) {

    int timeout = RandomUtils.nextInt(rangeStart, rangeEnd);
    logger.info("method={} latency={}", "generateLatency", timeout);

    try {
      Thread.sleep(timeout);
    } catch (InterruptedException e) {
      // do nothing
    }
  }

}
