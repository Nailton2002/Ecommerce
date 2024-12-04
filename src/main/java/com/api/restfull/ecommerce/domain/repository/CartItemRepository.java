package com.api.restfull.ecommerce.domain.repository;

import com.api.restfull.ecommerce.domain.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
