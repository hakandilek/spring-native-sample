package com.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Table("T_PRODUCT")
public class Product {

  @Id
  private Long id;
  
  @NotNull
  @Column("NAME")
  @JsonProperty
  private String name;

  @NotNull
  @Column("CODE")
  @JsonProperty
  private String code;

  public Long getId() {
    return id;
  }

  
  public String getName() { return name; }

  public String getCode() { return code; }

  public void setId(Long id) {
    this.id = id;
  }

  
  public void setName(String name) { this.name = name; }

  public void setCode(String code) { this.code = code; }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Product product = (Product) o;
    return Objects.equals(id, product.id)
        && Objects.equals(name, product.name)
        && Objects.equals(code, product.code);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Product{");
    sb.append("id=").append(id);
    sb.append(", name=").append(name);
    sb.append(", code=").append(code);
    sb.append('}');
    return sb.toString();
  }

}
