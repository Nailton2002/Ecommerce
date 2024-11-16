package com.api.restfull.ecommerce.domain.repository;

import com.api.restfull.ecommerce.domain.entity.Client;
import com.api.restfull.ecommerce.domain.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
