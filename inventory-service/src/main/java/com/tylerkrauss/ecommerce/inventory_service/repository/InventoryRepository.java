package com.tylerkrauss.ecommerce.inventory_service.repository;

import com.tylerkrauss.ecommerce.inventory_service.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
   boolean existsBySkuCodeAndQuantityIsGreaterThanEqual(String skuCode, Integer quantity);
   boolean existsBySkuCode(String skuCode);
}
