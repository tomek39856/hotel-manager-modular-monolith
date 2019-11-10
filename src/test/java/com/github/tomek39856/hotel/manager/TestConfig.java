package com.github.tomek39856.hotel.manager;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;

@Configuration
public class TestConfig {
  //public ApplicationEventPublisher testEventPublisher

  @Bean
  public BeanFactoryPostProcessor unregisterScheduling() {
    return beanFactory -> {
      for (String beanName : beanFactory.getBeanNamesForType(ScheduledAnnotationBeanPostProcessor.class)) {
        ((DefaultListableBeanFactory)beanFactory).removeBeanDefinition(beanName);
      }
    };
  }

}
