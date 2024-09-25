package com.tylerkrauss.ecommerce.order_service.consumer;

import com.tylerkrauss.ecommerce.order_service.model.StockInsufficientEvent;
import com.tylerkrauss.ecommerce.order_service.service.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class StockInsufficientConsumer {
   private final OrderService orderService;

   public StockInsufficientConsumer(OrderService orderService) {
      this.orderService = orderService;
   }

   @KafkaListener(topics = "stock-insufficient-topic", groupId = "order-group")
   public void handleStockInsufficientEvent(StockInsufficientEvent event) {
      System.out.println("Received StockInsufficientEvent for order ID: " + event.getOrderNumber());

      // Perform rollback or cancellation of the order
      orderService.cancelOrder(event.getOrderNumber(), event.getSkuCode());
   }
}
