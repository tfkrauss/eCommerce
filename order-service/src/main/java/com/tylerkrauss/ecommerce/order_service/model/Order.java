package com.tylerkrauss.ecommerce.order_service.model;


import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "t_orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;
   private String orderNumber;
   private String skuCode;
   private BigDecimal price;
   private Integer quantity;

}
