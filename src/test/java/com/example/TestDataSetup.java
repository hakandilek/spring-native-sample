package com.example;

import java.time.Instant;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class TestDataSetup implements BeforeAllCallback {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final Object lock = new Object();
  private static boolean initialized = false;

  @Override
  public void beforeAll(ExtensionContext context) {
    synchronized (lock) {
      if (!initialized) {
        initialized = true;
        logger.info("initializing test data...");

        final ApplicationContext ctx = SpringExtension.getApplicationContext(context);
        final TestData data = ctx.getBean(TestData.class);
        logger.debug("initial test data: {}", data);

        final ProductRepository productRepository = ctx.getBean(ProductRepository.class);
        logger.debug("creating Product...");
        final Product product = new Product();
        product.setName("Malayan Tapir");
        product.setCode("Malayan Tapir");
        data.setProduct(productRepository.deleteAll()
            .then(productRepository.save(product))
            .block());
        logger.info("test Product: {}", data.getProduct());

        logger.info("test data initialized: {}", data);
      }
    }
  }

}
