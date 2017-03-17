package com.kalnee.trivor.engine;

import com.kalnee.trivor.engine.dto.SubtitleDTO;
import com.kalnee.trivor.engine.insights.processors.SubtitleProcessor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.kalnee.trivor.engine.repositories.InsightsRepository;
import com.kalnee.trivor.engine.repositories.SubtitleRepository;

import static com.kalnee.trivor.engine.dto.TypeEnum.MOVIE;
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
          getClass().getClassLoader().getResource("language/gilmore/s1e1.srt").toURI(),
          new SubtitleDTO("tt0238784", "Gilmore Girls", 1, 1, 2006, TV_SHOW));
//      subtitleProcessor.process(
//          getClass().getClassLoader().getResource("language/gilmore/s1e2.srt").toURI(),
//          new SubtitleDTO("tt0238784", "Gilmore Girls", 1, 2, 2006, TV_SHOW));
//      subtitleProcessor.process(
//          getClass().getClassLoader().getResource("language/gilmore/s1e3.srt").toURI(),
//          new SubtitleDTO("tt0238784", "Gilmore Girls", 1, 3, 2006, TV_SHOW));
//      subtitleProcessor.process(
//          getClass().getClassLoader().getResource("language/gilmore/s1e4.srt").toURI(),
//          new SubtitleDTO("tt0238784", "Gilmore Girls", 1, 4, 2006, TV_SHOW));
//      subtitleProcessor.process(
//          getClass().getClassLoader().getResource("language/gilmore/s1e5.srt").toURI(),
//          new SubtitleDTO("tt0238784", "Gilmore Girls", 1, 5, 2006, TV_SHOW));
//      subtitleProcessor.process(
//          getClass().getClassLoader().getResource("language/gilmore/s1e6.srt").toURI(),
//          new SubtitleDTO("tt0238784", "Gilmore Girls", 1, 6, 2006, TV_SHOW));
//      subtitleProcessor.process(
//          getClass().getClassLoader().getResource("language/gilmore/s1e7.srt").toURI(),
//          new SubtitleDTO("tt0238784", "Gilmore Girls", 1, 7, 2006, TV_SHOW));
//      subtitleProcessor.process(
//          getClass().getClassLoader().getResource("language/gilmore/s1e8.srt").toURI(),
//          new SubtitleDTO("tt0238784", "Gilmore Girls", 1, 8, 2006, TV_SHOW));
//      subtitleProcessor.process(
//          getClass().getClassLoader().getResource("language/subtitle2.srt").toURI(),
//          new SubtitleDTO("tt1520211", "The Walking Dead", 1, 1, 2006, TV_SHOW));
//      subtitleProcessor.process(
//          getClass().getClassLoader().getResource("language/subtitle4.srt").toURI(),
//          new SubtitleDTO("tt4196776", "Jason Bourne", 1, 1, 2016, MOVIE));
//      subtitleProcessor.process(
//          getClass().getClassLoader().getResource("language/subtitle5.srt").toURI(),
//          new SubtitleDTO("tt0412142", "House M.D.", 1, 1, 2006, TV_SHOW));
//      subtitleProcessor.process(
//          getClass().getClassLoader().getResource("language/subtitle6.srt").toURI(),
//          new SubtitleDTO("tt3566726", "Jane The Virgin", 1, 1, 2006, TV_SHOW));
//      subtitleProcessor.process(
//          getClass().getClassLoader().getResource("language/subtitle7.srt").toURI(),
//          new SubtitleDTO("tt1280822", "Drop Dead Diva", 4, 2, 2013, TV_SHOW));
//      subtitleProcessor.process(
//          getClass().getClassLoader().getResource("language/bigbang/s3e5.srt").toURI(),
//          new SubtitleDTO("tt0898266", "Big Bang Theory", 3, 5, 2015, TV_SHOW));
//      subtitleProcessor.process(
//          getClass().getClassLoader().getResource("language/got/s2e2.srt").toURI(),
//          new SubtitleDTO("tt0944947", "Game Of Thrones", 2, 2, 2012, TV_SHOW));
//      subtitleProcessor.process(
//          getClass().getClassLoader().getResource("language/police-academy.srt").toURI(),
//          new SubtitleDTO("tt0087928", "Police Academy", 1987, MOVIE));
//      subtitleProcessor.process(
//          getClass().getClassLoader().getResource("language/12-angry-men.srt").toURI(),
//          new SubtitleDTO("tt0087928", "12 Angry Men", 1987, MOVIE));
//      subtitleProcessor.process(
//          getClass().getClassLoader().getResource("language/harry-potter.srt").toURI(),
//          new SubtitleDTO("tt0087928", "Harry Potter", 2011, MOVIE));
//      subtitleProcessor.process(
//          getClass().getClassLoader().getResource("language/lord-of-the-rings.srt").toURI(),
//          new SubtitleDTO("tt0087928", "Lord of the Rings", 2003, MOVIE));
//      subtitleProcessor.process(
//          getClass().getClassLoader().getResource("language/friends/s7e10.srt").toURI(),
//          new SubtitleDTO("tt0583522", "Friends", 7, 10, 2001, TV_SHOW));
    };
  }
}
