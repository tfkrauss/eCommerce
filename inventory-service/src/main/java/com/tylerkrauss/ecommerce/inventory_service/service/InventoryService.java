package com.tylerkrauss.ecommerce.inventory_service.service;

import com.tylerkrauss.ecommerce.inventory_service.model.Inventory;
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

   public boolean addNewItem(String skuCode, Integer quantity) {
      if (inventoryRepository.existsBySkuCode(skuCode)) {
         return false;
      }
      Inventory newItem = new Inventory();
      newItem.setQuantity(quantity);
      newItem.setSkuCode(skuCode);
      inventoryRepository.save(newItem);
      return true;
   }

}
