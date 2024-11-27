package com.api.restfull.ecommerce.application.request;

import com.api.restfull.ecommerce.application.response.OrderResponse;
import com.api.restfull.ecommerce.domain.enums.MethodPayment;
import com.api.restfull.ecommerce.domain.enums.StatusPayment;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentRequest(

        Long id,
        Long orderId,
        BigDecimal value,
        StatusPayment statusPayment,
        MethodPayment methodPayment,
        String numberTransaction,
        LocalDateTime dataPayment
) {
}
