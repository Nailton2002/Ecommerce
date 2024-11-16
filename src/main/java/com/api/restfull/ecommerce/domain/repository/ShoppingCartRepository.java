package com.api.restfull.ecommerce.domain.repository;

import com.api.restfull.ecommerce.domain.entity.Client;
import com.api.restfull.ecommerce.domain.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart findByCliente(Client client);
}
