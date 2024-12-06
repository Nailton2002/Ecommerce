package com.api.restfull.ecommerce.application.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public record PaymentResponse(

        String status, // Ex: SUCCESS, PENDING, FAILED
        BigDecimal amount, // Valor total do pagamento
        String message, // Mensagem descritiva sobre o pagamento
        String transactionId, // ID único da transação (opcional)
        CartResponse cart,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss'Z'", timezone = "GMT")
        LocalDateTime dataPayment
) {


    public static PaymentResponse fromCreditCard(BigDecimal amount, String transactionId, CartResponse cart, LocalDateTime dataPayment) {
        return new PaymentResponse("SUCCESS", amount, "Pagamento com cartão de crédito aprovado.", transactionId, cart, dataPayment);
    }

    public static PaymentResponse fromDebitCard(BigDecimal amount, String transactionId, CartResponse cart, LocalDateTime dataPayment) {
        return new PaymentResponse("SUCCESS", amount, "Pagamento com cartão de débito aprovado.", transactionId, cart, dataPayment);
    }

    public static PaymentResponse fromPix(BigDecimal amount, String transactionId, LocalDateTime dataPayment) {
        return new PaymentResponse("SUCCESS", amount, "Pagamento com PIX aprovado.", transactionId, null, dataPayment);
    }

    public static PaymentResponse fromBoleto(BigDecimal amount, String transactionId, LocalDateTime dataPayment) {
        return new PaymentResponse("PENDING", amount, "Boleto gerado. Aguardando pagamento.", transactionId, null, dataPayment);
    }

}
