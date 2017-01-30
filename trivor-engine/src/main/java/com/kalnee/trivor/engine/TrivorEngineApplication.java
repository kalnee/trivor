package com.kalnee.trivor.engine;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.kalnee.trivor.engine.repositories.InsightsRepository;
import com.kalnee.trivor.engine.repositories.SubtitleRepository;

@SpringBootApplication
@EnableEurekaClient
public class TrivorEngineApplication {

  public static void main(String[] args) {
    SpringApplication.run(TrivorEngineApplication.class, args);
  }

  @Bean
  InitializingBean seedMongoDB(SubtitleRepository subtitleRepository, InsightsRepository insightsRepository) {
		return () -> {
			subtitleRepository.deleteAll();
      insightsRepository.deleteAll();
		};
  }
}
