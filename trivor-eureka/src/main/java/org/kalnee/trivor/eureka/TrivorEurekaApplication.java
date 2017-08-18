package org.kalnee.trivor.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class TrivorEurekaApplication {

  public static void main(String[] args) {
    SpringApplication.run(TrivorEurekaApplication.class, args);
  }
}
