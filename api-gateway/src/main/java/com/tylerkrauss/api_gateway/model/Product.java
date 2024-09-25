package com.tylerkrauss.api_gateway.model;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class Product {
   private String id;
   private String skuCode;
   private String name;
   private String description;
   private BigDecimal price;
}