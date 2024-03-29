package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@RequiredArgsConstructor
@SpringBootApplication
public class SpringPcBtoApplication implements ApplicationRunner {

  public static void main(String[] args) {
    SpringApplication.run(SpringPcBtoApplication.class, args);
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {}
}
