package com.api.restfull.ecommerce.application.response;

import com.api.restfull.ecommerce.application.response.category.CategoryListResponse;
import com.api.restfull.ecommerce.application.response.product.ProductListResponse;
import com.api.restfull.ecommerce.domain.entity.CartItem;
import com.api.restfull.ecommerce.domain.entity.Category;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CartItemResponse(

        Long productId,
        String productName,
        Integer quantity,
        BigDecimal totalPrice
) {

    public static CartItemResponse fromEntityToResponse(CartItem cartItem) {

        return new CartItemResponse(
                cartItem.getId(),
                cartItem.getProduct().getName(),
                cartItem.getQuantity(),
                cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()))
        );

    }
}
