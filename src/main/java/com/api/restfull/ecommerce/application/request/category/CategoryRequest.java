package com.api.restfull.ecommerce.application.request.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
        @NotBlank
        String name,
        @NotBlank
        String description
) {

}
