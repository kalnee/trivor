package com.kalnee.trivor.engine;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.kalnee.trivor.engine.models.SubtitleModel;
import com.kalnee.trivor.engine.repositories.SubtitleRepository;

@SpringBootApplication
public class TrivorEngineApplication {

  public static void main(String[] args) {
    SpringApplication.run(TrivorEngineApplication.class, args);
  }

  @Bean
  InitializingBean seedMongoDB(SubtitleRepository subtitleRepository) {
    return () -> {
      subtitleRepository.deleteAll();
      subtitleRepository.insert(new SubtitleModel(100L, "Flash", 1, 10, 2013,
          Arrays.asList("That's nice.", "Don't move, please.", "Nobody's gonna see my feet.")));
      subtitleRepository.insert(new SubtitleModel(100L, "Flash", 1, 11, 2013,
          Arrays.asList("Why are you insisting on doing this?", "Come on!", "What?")));
      subtitleRepository.insert(new SubtitleModel(1200L, "Dexter", 2, 4, 2011,
          Arrays.asList("I want to kill again.", "Should I?", "Oh, yeah!")));
    };
  }

  @Bean
  CommandLineRunner runner(SubtitleRepository subtitleRepository) {
    return (args) -> {
			System.err.println("Phrases that use contractions:");
			subtitleRepository.findByYear(2013).stream()
				.flatMap(s -> s.getPhrases().stream())
				.filter(s -> s.contains("'"))
				.forEach(System.err::println);
		};
  }
}
