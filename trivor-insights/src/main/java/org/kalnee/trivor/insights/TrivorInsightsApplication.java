package org.kalnee.trivor.insights;

import org.kalnee.trivor.insights.repositories.InsightsRepository;
import org.kalnee.trivor.insights.repositories.SubtitleRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class TrivorInsightsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrivorInsightsApplication.class, args);
    }

    @Bean
    InitializingBean seedMongoDB(SubtitleRepository subtitleRepository, InsightsRepository insightsRepository) {
        return () -> {
            subtitleRepository.deleteAll();
            insightsRepository.deleteAll();
        };
    }
}
