package com.example.db;

import io.r2dbc.spi.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@EnableR2dbcRepositories(basePackages = {"com.example"})
@Configuration
public class DatabaseConfiguration {

  private final Logger logger = LoggerFactory.getLogger(getClass());
  private final ConnectionFactory connectionFactory;
  private final DatabaseMigrationProperties properties;

  @Autowired
  public DatabaseConfiguration(ConnectionFactory connectionFactory, DatabaseMigrationProperties properties) {
    this.connectionFactory = connectionFactory;
    this.properties = properties;
  }

  @Bean
  public ConnectionFactoryInitializer initializer() {

    ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
    initializer.setConnectionFactory(connectionFactory);

    CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
    if (properties.hasInitScript()) {
      String initScript = properties.getInitScript();
      logger.info("DB init script'{}'", initScript);
      populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource(initScript)));
    }
    if (properties.hasCleanScript()) {
      String cleanScript = properties.getCleanScript();
      logger.info("DB clean script'{}'", cleanScript);
      populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource(cleanScript)));
    }
    initializer.setDatabasePopulator(populator);

    return initializer;
  }

}
