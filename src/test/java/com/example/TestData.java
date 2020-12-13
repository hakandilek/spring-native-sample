package com.example;

import org.springframework.stereotype.Component;

@Component
public class TestData {

  private Product product;

  public Product getProduct() {
    return product;
  }


  public void setProduct(Product product) {
    this.product = product;
  }


  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("TestData {\n");
    sb.append(" product=").append(product).append("\n");
    sb.append('}');
    return sb.toString();
  }

}
