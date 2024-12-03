package com.api.restfull.ecommerce.application.response.product;

import com.api.restfull.ecommerce.application.response.category.CategoryListResponse;
import com.api.restfull.ecommerce.application.response.category.CategoryResponse;
import com.api.restfull.ecommerce.domain.entity.Product;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductListResponse(

        Long id,

        //Nome do produto
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
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy'T'HH:mm:ss'Z'", timezone = "GMT")
        LocalDateTime creationDate,

        //Nome da Categoria do produto
        String category

) {

    // Construtor que converte uma entidade `Product` para o DTO
    public ProductListResponse(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantityStock(),
                product.getActive(),
                product.getCreationDate() != null ? product.getCreationDate() : LocalDateTime.now(),
                product.getCategory().getName()
                );
    }
}
