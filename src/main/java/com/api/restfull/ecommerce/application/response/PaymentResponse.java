package com.api.restfull.ecommerce.application.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public record PaymentResponse(

        // Ex: SUCCESS, PENDING, FAILED
        String status,

        // Valor total do pagamento
        BigDecimal amount,

        // Mensagem descritiva sobre o pagamento
        String message,

        // ID único da transação (opcional)
        String transactionId,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss'Z'", timezone = "GMT")
        LocalDateTime dataPayment,

        CartResponse cart
) {


    public static PaymentResponse fromCreditCard(BigDecimal amount, String transactionId, CartResponse cart, LocalDateTime dataPayment) {
        return new PaymentResponse("SUCCESS", amount, "Pagamento com cartão de crédito aprovado.", transactionId, dataPayment, cart);
    }

    public static PaymentResponse fromDebitCard(BigDecimal amount, String transactionId, CartResponse cart, LocalDateTime dataPayment) {
        return new PaymentResponse("SUCCESS", amount, "Pagamento com cartão de débito aprovado.", transactionId, dataPayment, cart);
    }

    public static PaymentResponse fromPix(BigDecimal amount, String transactionId, LocalDateTime dataPayment) {
        return new PaymentResponse("SUCCESS", amount, "Pagamento com PIX aprovado.", transactionId, dataPayment,null);
    }

    public static PaymentResponse fromBoleto(BigDecimal amount, String transactionId, LocalDateTime dataPayment) {
        return new PaymentResponse("PENDING", amount, "Boleto gerado. Aguardando pagamento.", transactionId, dataPayment,null );
    }

}
