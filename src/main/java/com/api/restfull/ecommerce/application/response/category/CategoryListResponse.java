package com.api.restfull.ecommerce.application.response.category;

import com.api.restfull.ecommerce.application.response.ProductResponse;
import com.api.restfull.ecommerce.domain.entity.Category;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record CategoryListResponse(

        Long id,
        String name,
        String description,
        Boolean active,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy'T'HH:mm:ss'Z'", timezone = "GMT")
        LocalDateTime creationDate,
        List<ProductResponse> products
) {

    public CategoryListResponse(Category category) {
        this(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getActive(),
                category.getCreationDate() != null ? category.getCreationDate() : LocalDateTime.now(),
                category.getProducts() != null ? category.getProducts().stream().map(ProductResponse::new).toList() : List.of()
        );
    }

}
