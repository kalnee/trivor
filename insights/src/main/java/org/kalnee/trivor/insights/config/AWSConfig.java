package org.kalnee.trivor.insights.config;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClient;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.core.region.RegionProvider;
import org.springframework.cloud.aws.messaging.config.QueueMessageHandlerFactory;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.cloud.aws.messaging.listener.QueueMessageHandler;
import org.springframework.cloud.aws.messaging.listener.SimpleMessageListenerContainer;
import org.springframework.cloud.aws.messaging.support.NotificationMessageArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.messaging.converter.CompositeMessageConverter;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.support.PayloadArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
public class AWSConfig {

  public static final String SQS_POLLER_THREAD_NAME_PREFIX = "SQS-";

  // process sqs messages in parallel
  public static final int SQS_POLLER_THREAD_POOL_SIZE = 5;
  // if polling returned with no messages wait 2 seconds until next poll
  public static final int SQS_POLLER_WAIT_TIME_OUT = 2;

  @Autowired
  private AmazonSQSAsync sqsAsyncClient;

  @Autowired
  public BeanFactory beanFactory;

  @Bean
  public AmazonSQSAsync sqsAsyncClient() {
    return AmazonSQSAsyncClient.asyncBuilder().build();
  }

  @Bean
  public AsyncTaskExecutor sqsPollerExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(SQS_POLLER_THREAD_POOL_SIZE);
    executor.setThreadNamePrefix(SQS_POLLER_THREAD_NAME_PREFIX);
    executor.setWaitForTasksToCompleteOnShutdown(false);
    executor.initialize();
    return new ConcurrentTaskExecutor(executor);
  }

  @Bean
  public SimpleMessageListenerContainerFactory sqsMessageListenerContainerFactory() {
    SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
    factory.setTaskExecutor(sqsPollerExecutor());
    factory.setAmazonSqs(sqsAsyncClient);
    factory.setAutoStartup(true);
    factory.setWaitTimeOut(SQS_POLLER_WAIT_TIME_OUT);
    factory.setQueueMessageHandler(sqsMessageHandler());
    // consume the same number of messages as our thread pool can process
    factory.setMaxNumberOfMessages(SQS_POLLER_THREAD_POOL_SIZE);
    return factory;
  }

  @Bean
  public SimpleMessageListenerContainer sqsMessageListenerContainer() {
    SimpleMessageListenerContainerFactory factory = sqsMessageListenerContainerFactory();
    SimpleMessageListenerContainer container = factory.createSimpleMessageListenerContainer();
    container.setMessageHandler(factory.getQueueMessageHandler());
    return container;
  }

  // @Bean
  // public QueueMessageHandlerFactory queueMessageHandlerFactory() {
  // QueueMessageHandlerFactory factory = new QueueMessageHandlerFactory();
  // MappingJackson2MessageConverter jacksonMessageConverter = new
  // MappingJackson2MessageConverter();
  // jacksonMessageConverter.setSerializedPayloadClass(String.class);
  // jacksonMessageConverter.setStrictContentTypeMatch(false);
  // PayloadArgumentResolver payloadArgumentResolver =
  // new PayloadArgumentResolver(jacksonMessageConverter);
  // factory.setArgumentResolvers(Collections.singletonList(payloadArgumentResolver));
  //
  // return factory;
  // }

  @Bean
  public QueueMessageHandlerFactory sqsMessageHandlerFactory() {
    QueueMessageHandlerFactory factory = new QueueMessageHandlerFactory();
    factory.setBeanFactory(beanFactory);
    factory.setAmazonSqs(sqsAsyncClient);
    return factory;
  }

  @Bean
  public QueueMessageHandler sqsMessageHandler() {
    Collection<MessageConverter> converters = new ArrayList<>();
    MappingJackson2MessageConverter jacksonConverter = new MappingJackson2MessageConverter();
    jacksonConverter.setSerializedPayloadClass(String.class);
    jacksonConverter.setStrictContentTypeMatch(false);
    converters.add(jacksonConverter);

    List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>();
    CompositeMessageConverter compositeMessageConverter = new CompositeMessageConverter(converters);
    resolvers.add(new NotificationMessageArgumentResolver(compositeMessageConverter));
    resolvers.add(new PayloadArgumentResolver(compositeMessageConverter));

    QueueMessageHandler handler = sqsMessageHandlerFactory().createQueueMessageHandler();
    handler.setArgumentResolvers(resolvers);
    return handler;
  }
}
