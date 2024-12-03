package com.api.restfull.ecommerce.application.request.product;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductUpRequest(

        Long id,

        @NotBlank
        String name,

        @NotNull
        String description,

        @NotNull
        Integer quantityStock,

        @NotNull
        BigDecimal price,

        Boolean active,

        @NotNull
        Long categoryId) {

}

