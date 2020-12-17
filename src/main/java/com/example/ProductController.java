package com.example;

import static com.example.util.FluxUtils.wrapNotFoundIfEmpty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {

  private final Logger logger = LoggerFactory.getLogger(getClass());
  private final ProductRepository repository;
  

  public ProductController(ProductRepository repository) {
    this.repository = repository;
    
  }

  @GetMapping()
  public Flux<Product> listAll() {
    logger.debug("listAll()");
    return repository.findAll();
  }

  @GetMapping("/{id}")
  public Mono<Product> getOne(@PathVariable Long id) {
    logger.debug("getOne({})", id);
    return wrapNotFoundIfEmpty(repository.findById(id), "product", id);
  }

  @DeleteMapping("/{id}")
  public Mono<Product> delete(@PathVariable Long id) {
    logger.debug("delete({})", id);
    return wrapNotFoundIfEmpty(repository.findById(id)
        .flatMap(product -> repository.delete(product).thenReturn(product)), "product", id);
  }

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Product> create(@RequestBody Product product) {
    logger.debug("create({})", product);
    return repository.save(product);
  }

  @PutMapping("/{id}")
  public Mono<Product> update(@PathVariable Long id, @RequestBody Product entity) {
    logger.debug("create({} : {})", id, entity);
    entity.setId(id);
    return wrapNotFoundIfEmpty(repository.save(entity), "product", id);
  }
  
  
}
