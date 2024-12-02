package com.api.restfull.ecommerce.application.response;

import com.api.restfull.ecommerce.application.dto.AddressDto;
import com.api.restfull.ecommerce.domain.entity.Order;
import com.api.restfull.ecommerce.domain.enums.StatusOrder;
import com.api.restfull.ecommerce.domain.model.Address;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public record OrderResponse(
        Long id,
        ClientResponse client,
        List<OrderItemResponse> items,
        BigDecimal totalValue,
        String status, // Status do pedido (e.g., "PENDENTE", "CONCLUÍDO")
        LocalDateTime orderDate

) {



}
