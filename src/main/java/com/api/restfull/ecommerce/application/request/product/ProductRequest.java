package com.api.restfull.ecommerce.application.request.product;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductRequest(

        @Size(max = 100, message = "O nome do produto deve ter no máximo 100 caracteres")
        @NotBlank(message = "O nome do produto é obrigatório")
        String name,

        @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
        @NotBlank(message = "A descrição do produto é obrigatória.")
        String description,

        @NotNull
        Integer quantityStock,

        @NotNull(message = "O preço é obrigatório.")
        @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
        BigDecimal price,

        @Column(nullable = true)
        Boolean active,

        @NotNull
        Long categoryId,

        LocalDateTime creationDate) {

}

