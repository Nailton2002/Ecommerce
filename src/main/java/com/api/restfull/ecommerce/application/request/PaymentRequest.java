package com.api.restfull.ecommerce.application.request;

import com.api.restfull.ecommerce.domain.enums.PaymentMethod;
import com.api.restfull.ecommerce.domain.enums.StatusPayment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentRequest(

        Long id,
        Long orderId,
        BigDecimal value,
        StatusPayment statusPayment,
        PaymentMethod paymentMethod,
        String numberTransaction,
        LocalDateTime dataPayment
) {
}
