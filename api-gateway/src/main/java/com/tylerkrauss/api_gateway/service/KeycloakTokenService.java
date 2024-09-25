package com.tylerkrauss.api_gateway.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class KeycloakTokenService {

   private static final String KEYCLOAK_TOKEN_URL = "http://localhost:8181/realms/springboot-ecommerce-security/protocol/openid-connect/token";
   private static final String CLIENT_ID = "api-gateway-client";
   private static final String CLIENT_SECRET = "6zDQCuOG6Jwc2vkc5r6P6MrenQW9eCLf";

   private final RestTemplate restTemplate;

   public KeycloakTokenService(RestTemplate restTemplate) {
      this.restTemplate = restTemplate;
   }

   public String getAccessToken() {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

      // Form data for token request
      MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
      formData.add("client_id", CLIENT_ID);
      formData.add("client_secret", CLIENT_SECRET);
      formData.add("grant_type", "client_credentials");

      // Create request entity
      HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);

      // Send POST request to Keycloak to get the token
      ResponseEntity<Map> response = restTemplate.postForEntity(KEYCLOAK_TOKEN_URL, requestEntity, Map.class);

      // Extract and return the access token
      Map<String, String> responseBody = (Map<String, String>) response.getBody();
      return responseBody.get("access_token");
   }
}