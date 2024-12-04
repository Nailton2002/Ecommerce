package com.api.restfull.ecommerce.application.response;

import com.api.restfull.ecommerce.application.response.customer.CustomerResponse;
import com.api.restfull.ecommerce.domain.entity.Cart;
import com.api.restfull.ecommerce.domain.entity.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        String customerName,
        List<OrderItemResponse> items,
        BigDecimal total,
        LocalDateTime createdAt

) {


    public static OrderResponse fromCartToResponse(Order order) {

        return new OrderResponse(
                order.getId(),
                order.getCustomer().getName(),
                order.getItems().stream().map(OrderItemResponse::fromOrderItemToResponse).toList(),
                order.getTotal(),
                order.getCreationDate() != null ? order.getCreationDate() : LocalDateTime.now()

        );
    }

}
