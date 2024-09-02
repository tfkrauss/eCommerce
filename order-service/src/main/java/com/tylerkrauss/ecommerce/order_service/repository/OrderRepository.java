package com.tylerkrauss.ecommerce.order_service.repository;

import com.tylerkrauss.ecommerce.order_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
