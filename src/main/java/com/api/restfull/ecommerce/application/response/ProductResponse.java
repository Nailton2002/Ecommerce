package com.api.restfull.ecommerce.application.response;

import com.api.restfull.ecommerce.application.dto.AddressDto;
import com.api.restfull.ecommerce.domain.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(

        Long id,
        String name,
        // Preço unitário do product
        BigDecimal price,
        // Descrição detalhada do product
        String description,
        // quantity disponível em estoque
        Integer quantityStock,
        // Status do product (active/inactive)
        Boolean active,
        CategoryResponse category,
        LocalDateTime creationDate
) {

    // Construtor que converte uma entidade `Product` para o DTO
    public ProductResponse(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getQuantityStock(),
                product.getActive(),
                product.getCategory() != null ? new CategoryResponse(product.getCategory()) : null,
                product.getCreationDate() != null ? product.getCreationDate() : LocalDateTime.now()
                );
    }
}
