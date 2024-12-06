package com.api.restfull.ecommerce.application.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;


public record CreditCardPaymentRequest(

        @NotNull(message = "O ID do carrinho é obrigatório.")
        Long cartId,

      @NotNull(message = "O valor do pagamento é obrigatório.")
        @Positive(message = "O valor do pagamento deve ser positivo.")
        BigDecimal amount,

        @NotBlank(message = "O número do cartão é obrigatório.")
        String cardNumber,

        @NotBlank(message = "O nome no cartão é obrigatório.")
        String cardHolderName,

        @NotBlank(message = "A data de validade é obrigatória.")
        String cardExpiry,

        @NotBlank(message = "O CPF ou CNPJ do titular é obrigatório.")
        String cardCpfCnpj
) {
}
