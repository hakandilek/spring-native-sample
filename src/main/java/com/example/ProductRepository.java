package com.example;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;


public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {

}
