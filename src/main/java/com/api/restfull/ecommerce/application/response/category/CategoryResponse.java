package com.api.restfull.ecommerce.application.response.category;

import com.api.restfull.ecommerce.domain.entity.Category;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record CategoryResponse(

        Long id,
        String name,
        String description
) {

    public CategoryResponse(Category category) {
        this(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }

}
