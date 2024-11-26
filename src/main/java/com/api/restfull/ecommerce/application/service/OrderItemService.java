package com.api.restfull.ecommerce.application.service;

import com.api.restfull.ecommerce.application.request.OrderItemRequest;
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
    OrderItemResponse updateOrderItem(OrderItemRequest request);

    @Transactional
    void deleteOrderItem(Long id);
}
