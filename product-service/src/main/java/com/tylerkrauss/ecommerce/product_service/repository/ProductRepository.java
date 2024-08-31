package com.tylerkrauss.ecommerce.product_service.repository;

import com.tylerkrauss.ecommerce.product_service.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {

}
