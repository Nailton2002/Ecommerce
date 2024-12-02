package com.api.restfull.ecommerce.application.request;

public record CartItemRequest(

        Long productId,
        Integer quantity
) {
}
