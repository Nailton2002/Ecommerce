package com.api.restfull.ecommerce.application.response;

import java.math.BigDecimal;

public record CartItemResponse(

        Long productId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal totalPrice
) {
}
