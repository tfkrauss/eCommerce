package com.tylerkrauss.ecommerce.order_service.config;

import com.tylerkrauss.ecommerce.order_service.model.StockInsufficientEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

   @Value("${spring.kafka.bootstrap-servers}")
   private String bootstrapServers;

   @Bean
   public ConsumerFactory<String, StockInsufficientEvent> consumerFactory() {
      Map<String, Object> configProps = new HashMap<>();
      configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
      configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "order-group");
      configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
      configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
      configProps.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
      configProps.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
      // Specify the trusted packages
      configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "com.tylerkrauss.ecommerce.inventory_service.model");

      return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), new JsonDeserializer<>(StockInsufficientEvent.class));
   }

   @Bean
   public ConcurrentKafkaListenerContainerFactory<String, StockInsufficientEvent> kafkaListenerContainerFactory() {
      ConcurrentKafkaListenerContainerFactory<String, StockInsufficientEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
      factory.setConsumerFactory(consumerFactory());
      return factory;
   }
}