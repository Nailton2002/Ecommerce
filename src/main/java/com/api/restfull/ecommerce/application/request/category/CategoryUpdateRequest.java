package com.api.restfull.ecommerce.application.request.category;

import jakarta.persistence.Column;

public record CategoryUpdateRequest(

        Long id,
        String name,
        String description,
        Boolean active
) {

}
