package com.api.restfull.ecommerce.application.service;

import com.api.restfull.ecommerce.application.request.CartRequest;
import com.api.restfull.ecommerce.application.response.CartResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;

public interface CartService {

    @Transactional
    CartResponse createCart(CartRequest request);

    @Transactional
    CartResponse getByIdCart(Long id);

    @Transactional
    Page<CartResponse> getAllPagedCarts(int page, int size);

    @Transactional
    CartResponse updateCart(CartRequest request);

    @Transactional
    void deleteCart(Long id);

}
