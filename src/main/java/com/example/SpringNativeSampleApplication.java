package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(proxyBeanMethods = false)
@Configuration
@EnableConfigurationProperties(com.example.db.DatabaseMigrationProperties.class)
//mastic-pin-annotation
public class SpringNativeSampleApplication {

  public SpringNativeSampleApplication() {
  }

  public static void main(String[] args) {
    SpringApplication.run(SpringNativeSampleApplication.class, args);
  }

}
