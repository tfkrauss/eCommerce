package com.tylerkrauss.ecommerce.order_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {
   private String orderNumber;
   private String skuCode;
   private Integer quantity;
   private Double price;
}