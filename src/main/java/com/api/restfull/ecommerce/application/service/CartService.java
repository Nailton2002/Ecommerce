package com.api.restfull.ecommerce.application.service;

import com.api.restfull.ecommerce.application.request.CartItemRequest;
import com.api.restfull.ecommerce.application.request.CartRequest;
import com.api.restfull.ecommerce.application.response.CartResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;

public interface CartService {

    @Transactional
    CartResponse createCart(CartRequest request);

    @Transactional
    CartResponse addItemToCart(Long cartId, CartItemRequest request);

    @Transactional
    CartResponse removeItemToCart(Long cartId, CartItemRequest request);

    @Transactional
    CartResponse clearCart(Long cartId);


}
