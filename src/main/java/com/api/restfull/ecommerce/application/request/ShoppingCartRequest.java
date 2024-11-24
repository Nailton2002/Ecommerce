package com.api.restfull.ecommerce.application.request;

import com.api.restfull.ecommerce.application.response.ClientResponse;
import com.api.restfull.ecommerce.application.response.ProductResponse;
import com.api.restfull.ecommerce.domain.enums.StatusOrder;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;
import java.util.List;

public record ShoppingCartRequest(
        Long id,
        Long clientId,
        List<Long> productIds,
        Double sumOfItemsOfOrders,
        @Column(name = "cancelada") @Enumerated(EnumType.STRING)
        StatusOrder statusOrder,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
        LocalDateTime creationDat
) {
}
