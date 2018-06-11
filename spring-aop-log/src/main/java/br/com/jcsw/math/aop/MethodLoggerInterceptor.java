package br.com.jcsw.math.aop;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MethodLoggerInterceptor {

  private static final Logger logger = LoggerFactory.getLogger(MethodLoggerInterceptor.class);

  @Around("@annotation(LogExecutionInfo)")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

    long start = System.currentTimeMillis();
    Object result = null;
    String exception = null;

    try {
      result = joinPoint.proceed();

    } catch (Throwable t) {
      exception = ExceptionUtils.getRootCauseMessage(t);
      throw t;

    } finally {

      try {
        log(joinPoint.getArgs(), MethodSignature.class.cast(joinPoint.getSignature()), result,
            (System.currentTimeMillis() - start), exception);
      } catch (Exception logException) {
        logger.error("label=UNEXPECTED_ERROR", logException);
      }
    }

    return result;
  }

  private void log(Object[] args, MethodSignature methodSignature, Object result, long elapsedTime,
      String exception) {
    if(exception != null) {
      logger.info("class={} method={} args={} result={} elapsedTime={} exception={}",
          methodSignature.getMethod().getDeclaringClass().getSimpleName(),
          methodSignature.getMethod().getName(),
          args,
          result,
          elapsedTime,
          exception);
    } else {
      logger.info("class={} method={} args={} result={} elapsedTime={}",
          methodSignature.getMethod().getDeclaringClass().getSimpleName(),
          methodSignature.getMethod().getName(),
          args,
          result,
          elapsedTime);
    }
  }
}
