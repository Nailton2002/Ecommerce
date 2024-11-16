package com.api.restfull.ecommerce.domain.repository;

import com.api.restfull.ecommerce.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
