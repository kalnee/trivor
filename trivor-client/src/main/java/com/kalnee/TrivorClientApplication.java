package com.kalnee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class TrivorClientApplication {

  public static void main(String[] args) {
    SpringApplication.run(TrivorClientApplication.class, args);
  }
}
