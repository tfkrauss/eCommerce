package com.tylerkrauss.api_gateway.controller;

import com.tylerkrauss.api_gateway.model.InventoryRequest;
import com.tylerkrauss.api_gateway.model.Product;
import com.tylerkrauss.api_gateway.model.ProductWithInventoryRequest;
import com.tylerkrauss.api_gateway.service.KeycloakTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiGatewayController {

   private final RestTemplate restTemplate;
   private final KeycloakTokenService keycloakTokenService;

   @Autowired
   public ApiGatewayController(RestTemplate restTemplate, KeycloakTokenService keycloakTokenService) {
      this.restTemplate = restTemplate;
      this.keycloakTokenService = keycloakTokenService;
   }

   private static final String PRODUCT_SERVICE_URL = "http://localhost:9000/api/product";
   private static final String INVENTORY_SERVICE_URL = "http://localhost:9000/api/inventory";

   @PostMapping("/product-with-inventory")
   public ResponseEntity<Map<String, Object>> createProductAndInventory(@RequestBody ProductWithInventoryRequest request) {
      String accessToken = keycloakTokenService.getAccessToken();

      HttpHeaders headers = new HttpHeaders();
      headers.setBearerAuth(accessToken);

      HttpEntity<Product> productEntity = new HttpEntity<>(request.getProduct(), headers);
      ResponseEntity<Product> productResponse = restTemplate.postForEntity(PRODUCT_SERVICE_URL, productEntity, Product.class);

      if (!productResponse.getStatusCode().is2xxSuccessful()) {
         Map<String, Object> response = new HashMap<>();
         response.put("message", "Failed to create product");
         response.put("statusCode", productResponse.getStatusCode());
         return ResponseEntity.status(productResponse.getStatusCode()).body(response);
      }

      // Get the created product details
      Product createdProduct = productResponse.getBody();


      // Call InventoryService to update inventory using query parameters (skuCode, quantity)
      String inventoryServiceUrl = INVENTORY_SERVICE_URL + "?skuCode=" + createdProduct.getSkuCode() + "&quantity=" + request.getQuantity();
      HttpEntity<Void> inventoryEntity = new HttpEntity<>(headers);  // No body, just headers
      ResponseEntity<Boolean> inventoryResponse = restTemplate.exchange(inventoryServiceUrl, HttpMethod.POST, inventoryEntity, Boolean.class);

      if (!inventoryResponse.getStatusCode().is2xxSuccessful()) {
         Map<String, Object> response = new HashMap<>();
         response.put("message", "Product created, but failed to update inventory");
         response.put("statusCode", inventoryResponse.getStatusCode());
         return ResponseEntity.status(inventoryResponse.getStatusCode()).body(response);
      }

      Map<String, Object> response = new HashMap<>();
      response.put("message", "Product and inventory successfully created and updated");
      response.put("product", createdProduct);  // Include product details in response if needed
      return ResponseEntity.ok(response);
   }
}