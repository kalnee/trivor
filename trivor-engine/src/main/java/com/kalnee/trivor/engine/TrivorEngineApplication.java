package com.kalnee.trivor.engine;

import java.util.Arrays;

import com.kalnee.trivor.engine.models.Sentence;
import com.kalnee.trivor.engine.models.Token;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.kalnee.trivor.engine.models.Subtitle;
import com.kalnee.trivor.engine.repositories.SubtitleRepository;
import com.kalnee.trivor.engine.nlp.SubtitleProcessor;

@SpringBootApplication
public class TrivorEngineApplication {

  public static void main(String[] args) {
    SpringApplication.run(TrivorEngineApplication.class, args);
  }

  @Bean
  InitializingBean seedMongoDB(SubtitleRepository subtitleRepository) {
		return () -> {
			subtitleRepository.deleteAll();
			subtitleRepository.insert(new Subtitle("d231sw", "Flash", 1, 10, 2013,
					Arrays.asList(new Sentence("I want to kill you.", Arrays.asList(
							new Token("I", "PRP", 0.99), new Token("want", "VB", 0.99),
							new Token("to", "TO", 0.99), new Token("kill", "VB", 0.99),
							new Token("you", "PR", 0.99), new Token(".", ".", 0.99))))));
		};
  }

  @Bean
  CommandLineRunner runner(SubtitleRepository subtitleRepository, SubtitleProcessor subtitleProcessor) {
    return (args) -> {
			System.err.println("Phrases that use contractions:");
			subtitleRepository.findByYear(2013).stream()
				.flatMap(s -> s.getSentences().stream())
				.filter(s -> s.getSentence().contains("'"))
				.forEach(System.err::println);

        subtitleProcessor.process();
		};
  }
}
