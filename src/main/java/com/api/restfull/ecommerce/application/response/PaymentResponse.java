package com.api.restfull.ecommerce.application.response;

import com.api.restfull.ecommerce.domain.entity.Payment;
import com.api.restfull.ecommerce.domain.enums.StatusPayment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponse(
        Long id,
        OrderResponse order,
        BigDecimal value,
        StatusPayment statusPayment,
        String numberTransaction,
        LocalDateTime dataPayment) {

    public PaymentResponse(Payment payment) {
        this(
                payment.getId(),
                payment.getOrder() != null ? new OrderResponse(payment.getOrder()) : null,
                payment.getValue(),
                payment.getStatusPayment(),
                payment.getNumberTransaction(),
                payment.getDataPayment() != null ? payment.getDataPayment() : LocalDateTime.now()
        );
    }
}
