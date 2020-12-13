package com.example;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@ControllerTest
public class ProductControllerTest {

  @Autowired
  ApplicationContext context;

  @Autowired
  TestData data;

  @Autowired
  ProductRepository repository;

  WebTestClient rest;

  @BeforeEach
  public void setUp() {
    rest = WebTestClient.bindToApplicationContext(context)
        .configureClient()
        .responseTimeout(Duration.ofHours(1L))
        .build();
  }

  @Test
  public void createGetAndList() {

    Product entity = new Product();
    entity.setName("Malayan Tapir");
    entity.setCode("Malayan Tapir");

    Product created = rest.post()
        .uri("/products")
        .accept(MediaType.APPLICATION_JSON)
        .bodyValue(entity)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(Product.class)
        .returnResult()
        .getResponseBody();

    assertNotNull(created);
    assertEquals("Malayan Tapir", created.getName());
    assertEquals("Malayan Tapir", created.getCode());

    Product single = rest.get()
        .uri("/products/{id}", created.getId())
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Product.class)
        .returnResult()
        .getResponseBody();

    assertNotNull(single);
    assertEquals(created.getId(), single.getId());

    List<Product> list = rest.get()
        .uri("/products")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(new ParameterizedTypeReference<List<Product>>() {
        })
        .returnResult()
        .getResponseBody();

    assertNotNull(list);
    assertThat(list, not(empty()));
    assertThat(created, is(in(list)));
  }

  @Test
  public void createUpdateAndGet() {

    Product entity = new Product();
    entity.setName("Malayan Tapir");
    entity.setCode("Malayan Tapir");

    Product created = rest.post()
        .uri("/products")
        .accept(MediaType.APPLICATION_JSON)
        .bodyValue(entity)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(Product.class)
        .returnResult()
        .getResponseBody();

    assertNotNull(created);
    assertEquals("Malayan Tapir", created.getName());
    assertEquals("Malayan Tapir", created.getCode());

    entity.setName("Malayan Tapir Updated");
    entity.setCode("Malayan Tapir Updated");

    Product updated = rest.put()
        .uri("/products/{id}", created.getId())
        .accept(MediaType.APPLICATION_JSON)
        .bodyValue(entity)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Product.class)
        .returnResult()
        .getResponseBody();

    assertNotNull(updated);
    assertEquals("Malayan Tapir Updated", updated.getName());
    assertEquals("Malayan Tapir Updated", updated.getCode());

    Product single = rest.get()
        .uri("/products/{id}", created.getId())
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Product.class)
        .returnResult()
        .getResponseBody();

    assertNotNull(single);
    assertEquals(created.getId(), single.getId());
  }

  @Test
  public void createDeleteAndGet() {

    Product entity = new Product();
    entity.setName("Malayan Tapir");
    entity.setCode("Malayan Tapir");

    Product created = rest.post()
        .uri("/products")
        .accept(MediaType.APPLICATION_JSON)
        .bodyValue(entity)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(Product.class)
        .returnResult()
        .getResponseBody();

    assertNotNull(created);
    assertEquals("Malayan Tapir", created.getName());
    assertEquals("Malayan Tapir", created.getCode());

    Product deleted = rest.delete()
        .uri("/products/{id}", created.getId())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Product.class)
        .returnResult()
        .getResponseBody();

    assertNotNull(deleted);
    assertEquals(created.getId(), deleted.getId());

    rest.get()
        .uri("/products/{id}", created.getId())
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isNotFound();
  }

  @Test
  public void getInvalid() {
    rest.get()
        .uri("/products/{id}", "invalid")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isBadRequest();
  }

  @Test
  public void getMissing() {
    rest.get()
        .uri("/products/{id}", -666L)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isNotFound();
  }
  
  
}
