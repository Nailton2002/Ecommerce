package com.api.restfull.ecommerce.application.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OrderItemRequest(
        @NotNull(message = "O ID do Item do Pedido é obrigatório")
        Long id,
        @NotNull(message = "O ID do Pedido é obrigatório")
        Long orderId,
        @NotNull(message = "O ID do Produto é obrigatório")
        Long productId,
        @Positive(message = "A quantidade deve ser maior que zero")
        Integer quantity,
        @Positive(message = "O preço unitário deve ser maior que zero")
        BigDecimal unitPrice,
        BigDecimal subsumOfItemsOfOrders
) {
}
