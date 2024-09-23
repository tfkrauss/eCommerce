package com.tylerkrauss.ecommerce.product_service.dto;

import java.math.BigDecimal;

public record ProductRequest(String id, String skuCode, String name, String description, BigDecimal price) {
}
