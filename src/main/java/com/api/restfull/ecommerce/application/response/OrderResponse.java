package com.api.restfull.ecommerce.application.response;

import com.api.restfull.ecommerce.application.response.customer.CustomerResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        CustomerResponse client,
        List<OrderItemResponse> items,
        BigDecimal totalValue,
        String status, // Status do pedido (e.g., "PENDENTE", "CONCLUÍDO")
        LocalDateTime orderDate

) {



}
