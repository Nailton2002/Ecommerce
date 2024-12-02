package com.api.restfull.ecommerce.application.request;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(

        Long id,
        @NotBlank
        String name,
        @NotBlank
        String description,
        Boolean active

) {

}
