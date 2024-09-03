package com.tylerkrauss.ecommerce.inventory_service.service;

import com.tylerkrauss.ecommerce.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {

   private final InventoryRepository inventoryRepository;

   public boolean isInStock(String skuCode, Integer quantity) {
      return inventoryRepository.existsBySkuCodeAndQuantityIsGreaterThanEqual(skuCode, quantity);
   }

}
