package com.api.restfull.ecommerce.application.request;

import com.api.restfull.ecommerce.domain.entity.Category;
import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
        @NotBlank
        String name,
        @NotBlank
        String description,
        Boolean active
) {
    public CategoryRequest(Category category) {
        this(category.getName(), category.getDescription(), category.getActive());
    }

}
