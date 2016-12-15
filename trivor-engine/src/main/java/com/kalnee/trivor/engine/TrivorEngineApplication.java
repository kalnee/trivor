package com.kalnee.trivor.engine;

import java.util.Arrays;

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
          Arrays.asList("Hey, buddy!", "What's up?", "What are you doing over here?")));
      subtitleRepository.insert(new SubtitleModel(100L, "Flash", 1, 11, 2013,
          Arrays.asList("Did I do something wrong?", "Come on!", "What?")));
			subtitleRepository.insert(new SubtitleModel(1200L, "Dexter", 2, 4, 2011,
				Arrays.asList("I want to kill again.", "Should I?", "Oh, yeah!")));
    };
  }

  @Bean
  CommandLineRunner runner(SubtitleRepository subtitleRepository) {
    return (args) -> subtitleRepository.findByYear(2013).forEach(System.err::println);
  }
}
