package com.api.restfull.ecommerce.application.request;

import com.api.restfull.ecommerce.application.response.OrderResponse;
import com.api.restfull.ecommerce.application.response.ProductResponse;

import java.math.BigDecimal;

public record OrderItemRequest(
        Long id,
        Long orderId,
        Long productId,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subsumOfItemsOfOrders
) {
}
