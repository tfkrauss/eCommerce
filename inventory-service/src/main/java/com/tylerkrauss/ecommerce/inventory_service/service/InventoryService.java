package com.tylerkrauss.ecommerce.inventory_service.service;

import com.tylerkrauss.ecommerce.inventory_service.model.Inventory;
import com.tylerkrauss.ecommerce.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.tylerkrauss.ecommerce.inventory_service.model.OrderPlacedEvent;
import java.util.Optional;

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

   public void decrementStock(String skuCode, Integer amount) {
      Optional<Inventory> inventoryOptional = inventoryRepository.findBySkuCode(skuCode);
      if (inventoryOptional.isPresent()) {
         Inventory inventory = inventoryOptional.get();

         if (inventory.getQuantity() >= amount) {
            inventory.setQuantity(inventory.getQuantity() - amount);
            inventoryRepository.save(inventory);
            System.out.println("Decremented by " + amount);
         } else {
            throw new RuntimeException("Insufficient stock for SKU: " + skuCode);
         }
      } else {
         throw new RuntimeException("Item with SKU: " + skuCode + " not found.");
      }
   }

}
