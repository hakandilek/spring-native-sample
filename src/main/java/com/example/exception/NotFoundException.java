package com.example.exception;

import java.text.MessageFormat;

public class NotFoundException extends Throwable {

  private static final long serialVersionUID = 1L;
  private final String entity;
  private final Object identifier;

  public NotFoundException(String entity, Object identifier) {
    this.entity = entity;
    this.identifier = identifier;
  }

  public String getEntity() {
    return entity;
  }

  public Object getIdentifier() {
    return identifier;
  }

  @Override
  public String toString() {
    return MessageFormat.format("{0}({1}) is not found", entity, identifier);
  }

}
