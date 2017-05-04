package com.kalnee.trivor.engine.config;

import java.util.Collections;

import org.springframework.cloud.aws.messaging.config.QueueMessageHandlerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.PayloadArgumentResolver;

@Configuration
public class AWSConfig {

  @Bean
  public QueueMessageHandlerFactory queueMessageHandlerFactory() {
    QueueMessageHandlerFactory factory = new QueueMessageHandlerFactory();
    MappingJackson2MessageConverter jacksonMessageConverter = new MappingJackson2MessageConverter();
    jacksonMessageConverter.setSerializedPayloadClass(String.class);
    jacksonMessageConverter.setStrictContentTypeMatch(false);
    PayloadArgumentResolver payloadArgumentResolver =
        new PayloadArgumentResolver(jacksonMessageConverter);
    factory.setArgumentResolvers(Collections.singletonList(payloadArgumentResolver));

    return factory;
  }
}
