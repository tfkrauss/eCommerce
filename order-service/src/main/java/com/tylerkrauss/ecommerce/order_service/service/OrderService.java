package com.tylerkrauss.ecommerce.order_service.service;

import com.tylerkrauss.ecommerce.order_service.client.InventoryClient;
import com.tylerkrauss.ecommerce.order_service.dto.OrderRequest;
import com.tylerkrauss.ecommerce.order_service.model.Order;
import com.tylerkrauss.ecommerce.order_service.model.OrderPlacedEvent;
import com.tylerkrauss.ecommerce.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

   private final OrderRepository orderRepository;
   private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

   @Transactional
   public void placeOrder(OrderRequest orderRequest) {
      // Create and save the order
      Order order = new Order();
      order.setOrderNumber(UUID.randomUUID().toString());
      order.setPrice(orderRequest.price());
      order.setSkuCode(orderRequest.skuCode());
      order.setQuantity(orderRequest.quantity());
      orderRepository.save(order);

      // Publish an event to Kafka for stock update
      kafkaTemplate.send("order-placed-topic", new OrderPlacedEvent(order.getSkuCode(), order.getQuantity(), order.getOrderNumber()));
      System.out.println("OrderPlacedEvent sent: " + new OrderPlacedEvent(order.getSkuCode(), order.getQuantity(), order.getOrderNumber()));
   }

   public void cancelOrder(String orderNumber, String skuCode) {
      Optional<Order> orderOptional = orderRepository.findByOrderNumber(orderNumber);
      System.out.println("Cancelling order with ID: " + orderNumber);
      if (orderOptional.isPresent()) {
         Order order = orderOptional.get();
         orderRepository.delete(order);
         System.out.println("Successfully removed order: " + order);
      } else {
         throw new RuntimeException("Could not find order with orderNumber: " + orderNumber);
      }
   }
}
