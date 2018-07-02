package br.com.jcsw.math.chaosmonkey;

import java.lang.reflect.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * Identifies points configured to use Chaos Monkey
 */
@Configuration
class ChaosMonkeyScanner implements ApplicationContextAware {

  @Autowired
  private ChaosMonkeyRegister chaosMonkeyRegister;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {

    for (String beanName : applicationContext.getBeanDefinitionNames()) {
      Object beanInstance = applicationContext.getBean(beanName);
      Class<?> beanClass = beanInstance.getClass();

      if(org.springframework.aop.support.AopUtils.isAopProxy(beanInstance)) {
        beanClass = org.springframework.aop.support.AopUtils.getTargetClass(beanInstance);
      }

      for (Method method : beanClass.getDeclaredMethods()) {
        if(method.isAnnotationPresent(ChaosMonkey.class)) {
          chaosMonkeyRegister.addMethodToChaosMonkey(method.getName());
        }
      }

    }
  }

}
