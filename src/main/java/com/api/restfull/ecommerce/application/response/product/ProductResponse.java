package com.api.restfull.ecommerce.application.response;

import com.api.restfull.ecommerce.application.response.category.CategoryResponse;
import com.api.restfull.ecommerce.domain.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(

        Long id,

        String name,

        // Descrição detalhada do product
        String description,

        // Preço unitário do product
        BigDecimal price,

        // quantity disponível em estoque
        Integer quantityStock,

        // Status do product (active/inactive)
        Boolean active,

        //Data da criação do produto
        LocalDateTime creationDate,

        CategoryResponse category

) {

    // Construtor que converte uma entidade `Product` para o DTO
    public ProductResponse(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantityStock(),
                product.getActive(),
                product.getCreationDate() != null ? product.getCreationDate() : LocalDateTime.now(),
                product.getCategory() != null ? new CategoryResponse(product.getCategory()) : null
                );
    }
}
