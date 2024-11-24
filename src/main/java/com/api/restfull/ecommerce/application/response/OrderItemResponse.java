package com.api.restfull.ecommerce.application.response;

import com.api.restfull.ecommerce.domain.entity.OrderItem;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long id,
        OrderResponse order,
        ProductResponse product,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subsumOfItemsOfOrders
) {
    public OrderItemResponse(OrderItem orderItem) {
        this(
                orderItem.getId(),
                orderItem.getOrder() != null ? new OrderResponse(orderItem.getOrder()) : null,
                orderItem.getProduct() != null ? new ProductResponse(orderItem.getProduct()) : null,
                orderItem.getQuantity(),
                orderItem.getUnitPrice(),
                orderItem.getSubsumOfItemsOfOrders()
        );

    }
}
