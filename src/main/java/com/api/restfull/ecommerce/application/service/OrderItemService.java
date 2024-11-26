package com.api.restfull.ecommerce.application.service;

import com.api.restfull.ecommerce.application.request.OrderItemRequest;
import com.api.restfull.ecommerce.application.request.OrderRequest;
import com.api.restfull.ecommerce.application.response.OrderItemResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface OrderItemService {

    @Transactional
    OrderItemResponse saveOrderItem(OrderItemRequest request);

    @Transactional
    Page<OrderItemResponse> getAllPagedOrderItems(int page, int size);

    @Transactional
    OrderItemResponse findByIdOrderItem(Long id);

    @Transactional
    OrderItemResponse updateOrderItem(OrderRequest request);

    void deleteOrderItem(Long orderId);
}
