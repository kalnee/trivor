package com.kalnee.trivor.engine;

import com.kalnee.trivor.engine.dto.SubtitleDTO;
import com.kalnee.trivor.engine.dto.TypeEnum;
import com.kalnee.trivor.engine.insights.processors.SubtitleProcessor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.kalnee.trivor.engine.repositories.InsightsRepository;
import com.kalnee.trivor.engine.repositories.SubtitleRepository;

import static com.kalnee.trivor.engine.dto.TypeEnum.TV_SHOW;

@SpringBootApplication
@EnableEurekaClient
public class TrivorEngineApplication {

  public static void main(String[] args) {
    SpringApplication.run(TrivorEngineApplication.class, args);
  }

  @Bean
  InitializingBean seedMongoDB(SubtitleRepository subtitleRepository,
      InsightsRepository insightsRepository) {
    return () -> {
      subtitleRepository.deleteAll();
      insightsRepository.deleteAll();
    };
  }

  @Bean
  CommandLineRunner runner(SubtitleProcessor subtitleProcessor) {
    return (args) -> {
      subtitleProcessor.process(
          getClass().getClassLoader().getResource("language/subtitle.srt").toURI(),
          new SubtitleDTO("tt0238784", "Gilmore Girls", 1, 1, 2006, TV_SHOW));
    };
  }
}
