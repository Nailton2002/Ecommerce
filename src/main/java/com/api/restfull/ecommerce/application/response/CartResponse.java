package com.api.restfull.ecommerce.application.response;

import com.api.restfull.ecommerce.application.response.customer.CustomerResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CartResponse(
        Long id,
        CustomerResponse user,
        List<CartItemResponse> items,
        BigDecimal totalValue,
        LocalDateTime creationDate
) {


}
