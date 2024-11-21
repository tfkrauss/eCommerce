# eCommerce Microservices Project

This is a scalable eCommerce platform built using a microservices architecture. The system handles product management, order processing, inventory management, and more. It leverages Spring Boot, Kafka, and Docker to achieve modularity, fault tolerance, and high performance.

---

## Features

- **Product Service**: Manage product details (e.g., name, price, SKU).
- **Inventory Service**: Track product stock levels and validate availability.
- **Order Service**: Process customer orders and handle order rollbacks.
- **API Gateway**: Acts as a single entry point to route requests to the appropriate services.
- **Kafka Messaging**: Enables asynchronous communication between services for events such as order failure.
- **Database Integration**: Utilizes MySQL and MongoDB for data persistence.
- **Authentication**: Secured using Keycloak with role-based access control.

---

## Architecture Overview

The application consists of the following services:

1. **API Gateway**  
   - Handles incoming requests and routes them to the respective microservices.
   - Port: `9000`

2. **Product Service**  
   - Manages product catalog and exposes product-related APIs.
   - Stack: Spring Boot, MySQL

3. **Inventory Service**  
   - Tracks product stock levels.
   - Listens to order events and validates stock availability.
   - Stack: Spring Boot, Kafka, MySQL

4. **Order Service**  
   - Processes orders and handles rollbacks for insufficient stock.
   - Listens to stock insufficient events for corrective actions.
   - Stack: Spring Boot, Kafka, MongoDB

5. **Message Broker**  
   - **Kafka Topics**:
     - `order-placed-topic`: Published when an order is placed.
     - `stock-insufficient-topic`: Published when stock validation fails.

6. **Authentication**  
   - Keycloak used for securing endpoints with OAuth2.
