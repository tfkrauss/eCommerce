package com.tylerkrauss.ecommerce.inventory_service.consumer;


import com.tylerkrauss.ecommerce.inventory_service.model.OrderPlacedEvent;
import com.tylerkrauss.ecommerce.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Value;
import com.tylerkrauss.ecommerce.inventory_service.model.StockInsufficientEvent;

@Component
@RequiredArgsConstructor
public class OrderPlacedEventConsumer {

   private final InventoryService inventoryService;
   private final KafkaTemplate<String, StockInsufficientEvent> kafkaTemplate;

   @Value("${kafka.topic.stockInsufficient}")
   private String stockInsufficientTopic;

   @KafkaListener(topics = "order-placed-topic", groupId = "inventory-group")
   public void consumeOrderPlacedEvent(OrderPlacedEvent event) {
      System.out.println("Received OrderPlacedEvent: " + event);


      try {
         // Process the event
         inventoryService.decrementStock(event.getSkuCode(), event.getQuantity());
         System.out.println("Order processed successfully for SKU: " + event.getSkuCode());
      } catch (RuntimeException ex) {
         // Handle the exception gracefully
         System.err.println("Failed to process order for SKU: " + event.getSkuCode() + ". Error: " + ex.getMessage());
         // Publish the stock insufficient event to trigger a rollback in OrderService
         StockInsufficientEvent stockInsufficientEvent = new StockInsufficientEvent(event.getOrderNumber(), event.getSkuCode());
         kafkaTemplate.send(stockInsufficientTopic, stockInsufficientEvent);
      }
   }
}