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
        OrderResponse order,
        BigDecimal value,
        @Column(name = "cancelado") @Enumerated(EnumType.STRING)
        StatusPayment statusPayment,
        @Column(name = "CARTAO_CREDITO") @Enumerated(EnumType.STRING)
        MethodPayment methodPayment,
        @Column(unique = true, nullable = false)
        String numberTransaction,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss'Z'", timezone = "GMT")
        LocalDateTime dataPayment
) {
}
