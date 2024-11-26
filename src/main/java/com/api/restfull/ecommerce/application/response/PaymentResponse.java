package com.api.restfull.ecommerce.application.response;

import com.api.restfull.ecommerce.domain.entity.Payment;
import com.api.restfull.ecommerce.domain.enums.MethodPayment;
import com.api.restfull.ecommerce.domain.enums.StatusPayment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponse(
        Long id,
        // Pedido associado ao pagamento
        OrderResponse order,
        // Valor total do pagamento (soma dos itens do pedido)
        BigDecimal value,
        // Status do pagamento (ex: PENDENTE, APROVADO, REJEITADO, CANCELADO)
        String statusPayment,
        // Método de pagamento (ex: CARTAO_CREDITO, BOLETO, PIX)
        String methodPayment,
        // Número de transação (gerado pelo processador de pagamento)
        String numberTransaction,
        // Data de realização do pagamento
        LocalDateTime dataPayment,
        // Data de criação do registro do pagamento
        LocalDateTime creationDate
) {
    public PaymentResponse(Payment payment) {
        this(
                payment.getId(),
                payment.getOrder() != null ? new OrderResponse(payment.getOrder()) : null,
                payment.getValue(),
                payment.getStatusPayment() != null ? payment.getStatusPayment().toString() : "Status não definido",
                payment.getMethodPayment() != null ? payment.getMethodPayment().toString() : "Método não definido",
                payment.getNumberTransaction(),
                payment.getDataPayment() != null ? payment.getDataPayment() : LocalDateTime.now(),
                payment.getCreationDate() != null ? payment.getCreationDate() : LocalDateTime.now()
        );
    }

}
