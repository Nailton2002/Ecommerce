package com.api.restfull.ecommerce.application.response;

import com.api.restfull.ecommerce.domain.entity.Product;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(
        Long id,
        String name,
        // Preço unitário do product
        BigDecimal price,
        CategoryResponse category,

//      List<OrderResponse> order,

        // Descrição detalhada do product
        String description,
        // quantity disponível em estoque
        Integer quantityStock,
        // Status do product (active/inactive)
        Boolean active,
        // Datas de criação e última atualização do product
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
        LocalDateTime creationDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
        LocalDateTime lastUpdateDate) {

    // Construtor que converte uma entidade `Product` para o DTO
    public ProductResponse(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory() != null ? new CategoryResponse(product.getCategory()) : null,
                product.getDescription(),
                product.getQuantityStock(),
                product.getActive(),
                product.getCreationDate() != null ? product.getCreationDate() : LocalDateTime.now(),
                product.getLastUpdateDate() != null ? product.getLastUpdateDate() : LocalDateTime.now()
        );
    }
}
