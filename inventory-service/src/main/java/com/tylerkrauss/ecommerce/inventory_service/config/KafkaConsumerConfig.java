package com.tylerkrauss.ecommerce.inventory_service.config;

import com.tylerkrauss.ecommerce.inventory_service.model.OrderPlacedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

   @Value("${kafka.topic.orderPlaced}")
   private String orderPlacedTopic;

   @Bean
   public ConsumerFactory<String, OrderPlacedEvent> consumerFactory() {
      Map<String, Object> config = new HashMap<>();

      config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
      config.put(ConsumerConfig.GROUP_ID_CONFIG, "inventory-group");
      config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
      config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
      config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
      config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.tylerkrauss.ecommerce.inventory_service.model.OrderPlacedEvent");
      config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
      config.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
      return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer<>(OrderPlacedEvent.class));
   }

   @Bean
   public ConcurrentKafkaListenerContainerFactory<String, OrderPlacedEvent> kafkaListenerContainerFactory() {
      ConcurrentKafkaListenerContainerFactory<String, OrderPlacedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
      factory.setConsumerFactory(consumerFactory());
      return factory;
   }
}