package com.api.restfull.ecommerce.application.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record DebitCardPaymentRequest(

        @NotNull(message = "O ID do carrinho é obrigatório.")
        Long cartId,

        @NotBlank(message = "O número do cartão é obrigatório.")
        String cardNumber,

        @NotBlank(message = "A data de validade é obrigatória.")
        String expirationDate,

        @NotBlank(message = "O codigo de segurança é obrigatória.")
        String securityCode,

        @Positive(message = "O valor do pagamento deve ser positivo.")
        BigDecimal amount
) {
}
