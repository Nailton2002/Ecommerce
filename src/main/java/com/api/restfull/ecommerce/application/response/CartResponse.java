package com.api.restfull.ecommerce.application.response;

import com.api.restfull.ecommerce.domain.entity.Cart;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


public record CartResponse(

        Long id,
        String customerName,
        List<CartItemResponse> items,
        BigDecimal total,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss'Z'", timezone = "GMT")
        LocalDateTime creationDate
) {

    public static CartResponse fromCartToResponse(Cart cart) {

        return new CartResponse(
                cart.getId(),
                cart.getCustomer().getName(),
                cart.getItems().stream().map(CartItemResponse::fromCartItemToResponse).toList(),
                cart.getTotal(),
                cart.getCreationDate() != null ? cart.getCreationDate() : LocalDateTime.now()

        );
    }

}
