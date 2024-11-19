package com.api.restfull.ecommerce.application.request;

import com.api.restfull.ecommerce.domain.entity.Product;

import java.math.BigDecimal;

public record ProductRequest(
        Long id,
        String name,
        BigDecimal price,
        Long categoryId,
        String description,
        Integer quantityStock,
        Boolean active) {

    public ProductRequest(Product product) {
        this(
                product.getId(),
                product.getname(),
                product.getprice(),
                product.getcategory() != null ? product.getcategory().getId() : null,
                product.getdescription(),
                product.getquantityStock(),
                product.getactive()
        );
    }

}

