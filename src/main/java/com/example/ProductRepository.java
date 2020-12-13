package com.example;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {

}
