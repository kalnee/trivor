package com.kalnee.client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;

import com.kalnee.client.clients.EngineClient;
import com.kalnee.client.clients.SubtitleClient;
import com.kalnee.client.dto.SubtitleRequestDTO;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class TrivorClientApplication {

  public static void main(String[] args) {
    SpringApplication.run(TrivorClientApplication.class, args);
  }

  @Bean
  CommandLineRunner runner(EngineClient engineClient, SubtitleClient subtitleClient) {
    return (args) -> {
      System.out.println(engineClient.getInsightsByImdb("tt0238784").toString());
      System.out.println(subtitleClient.getInfo().toString());
      ResponseEntity<Object> response = subtitleClient.create(new SubtitleRequestDTO("tt4034228"));
      System.out.println("Response: " + response.getStatusCodeValue());
    };
  }
}