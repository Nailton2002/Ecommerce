package com.api.restfull.ecommerce.application.service;

import com.api.restfull.ecommerce.application.request.OrderRequest;
import com.api.restfull.ecommerce.application.response.OrderResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    @Transactional
    OrderResponse saveOrder(OrderRequest request);

    @Transactional
    Page<OrderResponse> getAllPagedOrders(int page, int size);

    @Transactional
    OrderResponse findByIdOrder(Long id);

    @Transactional
    OrderResponse updateOrder(OrderRequest request);

    void deleteOrder(Long orderId);
}
