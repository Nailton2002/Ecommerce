package com.api.restfull.ecommerce.application.response;

import com.api.restfull.ecommerce.application.response.product.ProductResponse;
import com.api.restfull.ecommerce.domain.entity.Order;
import com.api.restfull.ecommerce.domain.entity.OrderItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderItemResponse(

        String productName,
        Integer quantity,
        BigDecimal totalPrice

) {
    public static OrderItemResponse fromOrderItemToResponse(OrderItem orderItem) {

        return new OrderItemResponse(
                orderItem.getProduct().getName(),
                orderItem.getQuantity(),
                orderItem.getTotalPrice()

        );
    }
}

