package com.example.util;

import com.example.exception.NotFoundException;
import reactor.core.publisher.Mono;

public class FluxUtils {

  private FluxUtils() {
  }

  public static <T, ID> Mono<T> wrapNotFoundIfEmpty(Mono<T> finder, String entity, ID identifier) {
    return finder.switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException(entity, identifier))));
  }

}
