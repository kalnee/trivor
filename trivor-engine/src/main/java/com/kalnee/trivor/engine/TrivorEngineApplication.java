package com.kalnee.trivor.engine;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;

import com.amazonaws.services.s3.AmazonS3Client;
import com.kalnee.trivor.engine.dto.SubtitleDTO;
import com.kalnee.trivor.engine.models.Sentence;
import com.kalnee.trivor.engine.models.Token;
import com.kalnee.trivor.engine.repositories.InsightsRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.kalnee.trivor.engine.models.Subtitle;
import com.kalnee.trivor.engine.repositories.SubtitleRepository;
import com.kalnee.trivor.engine.insights.processors.SubtitleProcessor;

@SpringBootApplication
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

  @Bean
  CommandLineRunner runner(SubtitleProcessor subtitleProcessor) {
    return (args) -> {
        subtitleProcessor.process(
            getClass().getClassLoader().getResource("language/subtitle.srt").toURI(),
            new SubtitleDTO("tt0238784", "Gilmore Girls", 1, 1, 2006)
        );
    };
  }
}
