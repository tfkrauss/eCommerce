package com.tylerkrauss.ecommerce.order_service.repository;

import com.tylerkrauss.ecommerce.order_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

   boolean existsByOrderNumber(String orderNumber);

   Optional<Order> findByOrderNumber(String orderNumber);
}
