package com.api.restfull.ecommerce.application.request;

import com.api.restfull.ecommerce.domain.entity.Product;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequest(
        Long id,
        @NotBlank
        String name,
        @NotNull(message = "O preço é obrigatório.")
        @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
        BigDecimal price,
        @NotNull
        Long categoryId,
        @NotBlank
        String description,
        @NotNull
        Integer quantityStock,
        Boolean active) {

    public ProductRequest(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory() != null ? product.getCategory().getId() : null,
                product.getDescription(),
                product.getQuantityStock(),
                product.getActive()
        );
    }

}

