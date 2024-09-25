package com.tylerkrauss.ecommerce.inventory_service.repository;

import com.tylerkrauss.ecommerce.inventory_service.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
   boolean existsBySkuCodeAndQuantityIsGreaterThanEqual(String skuCode, Integer quantity);
   boolean existsBySkuCode(String skuCode);

   Optional<Inventory> findBySkuCode(String skuCode);
}
