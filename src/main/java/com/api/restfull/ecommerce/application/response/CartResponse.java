package com.api.restfull.ecommerce.application.response;

import com.api.restfull.ecommerce.application.response.customer.CustomerResponse;
import com.api.restfull.ecommerce.domain.entity.Cart;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record CartResponse(

        Long id,
        String customerName,
        List<CartItemResponse> items,
        BigDecimal totalValue,
        LocalDateTime creationDate
) {

    public static CartResponse fromCartToResponse(Cart cart) {

        return new CartResponse(
                cart.getId(),
                cart.getCustomer().getName(),
                cart.getItems().stream().map(CartItemResponse::fromEntityToResponse).toList(),
                cart.getTotal(),
                cart.getCreationDate() != null ? cart.getCreationDate() : LocalDateTime.now()

        );
    }

}
