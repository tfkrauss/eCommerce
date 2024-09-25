package com.tylerkrauss.api_gateway.model;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class ProductWithInventoryRequest {
   private Product product;
   private int quantity;
}