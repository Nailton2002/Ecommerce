package com.api.restfull.ecommerce.application.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PixPaymentRequest(

        @NotNull(message = "O ID do carrinho é obrigatório.")
        Long cartId,

        // Chave PIX
        String pixKey,

        // Valor a ser pago
        BigDecimal amount
) {
}
