package com.api.restfull.ecommerce.application.response;

import com.api.restfull.ecommerce.application.response.category.CategoryListResponse;
import com.api.restfull.ecommerce.application.response.product.ProductListResponse;
import com.api.restfull.ecommerce.domain.entity.CartItem;
import com.api.restfull.ecommerce.domain.entity.Category;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CartItemResponse(

        Long productId,
        String productName,
        Integer quantity,
        BigDecimal totalPrice
) {

    public static CartItemResponse fromCartItemToResponse(CartItem cartItem) {

        return new CartItemResponse(
                cartItem.getProduct().getId(),
                cartItem.getProduct().getName(),
                cartItem.getQuantity(),
                cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()))
        );
    }


}
