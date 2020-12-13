package com.example.exception;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class ExceptionHandler implements WebExceptionHandler {

  static final String ENTITY = "entity";
  static final String ID = "id";
  static final String MESSAGE = "message";

  @Override
  public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
    if (ex instanceof NotFoundException) {
      final NotFoundException nfe = (NotFoundException) ex;
      ServerHttpResponse response = exchange.getResponse();
      response.setStatusCode(HttpStatus.NOT_FOUND);
      HttpHeaders headers = response.getHeaders();
      headers.add(ENTITY, nfe.getEntity());
      headers.add(ID, nfe.getIdentifier() + "");
      headers.add(MESSAGE, nfe.toString());
    } else if (ex instanceof ResponseStatusException) {
      final ResponseStatusException rse = (ResponseStatusException) ex;
      ServerHttpResponse response = exchange.getResponse();
      response.setStatusCode(rse.getStatus());
      HttpHeaders headers = response.getHeaders();
      headers.add(MESSAGE, rse.getMessage());
    }
    return Mono.empty();
  }
}
