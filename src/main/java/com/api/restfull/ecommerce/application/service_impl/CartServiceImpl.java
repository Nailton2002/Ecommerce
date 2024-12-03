package com.api.restfull.ecommerce.application.service_impl;

import com.api.restfull.ecommerce.application.request.CartRequest;
import com.api.restfull.ecommerce.application.response.CartResponse;
import com.api.restfull.ecommerce.application.service.CartService;
import com.api.restfull.ecommerce.domain.repository.CartRepository;
import com.api.restfull.ecommerce.domain.repository.CustomerRepository;
import com.api.restfull.ecommerce.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository repository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Override
    public CartResponse createCart(CartRequest request) {
     return null;
    }

    @Override
    public CartResponse getByIdCart(Long id) {
        return null;
    }

    @Override
    public Page<CartResponse> getAllPagedCarts(int page, int size) {
        return null;
    }

    @Override
    public CartResponse updateCart(CartRequest request) {
        return null;
    }

    @Override
    public void deleteCart(Long id) {

    }
}
