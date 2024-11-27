package com.api.restfull.ecommerce.application.service;

import com.api.restfull.ecommerce.application.request.PaymentRequest;
import com.api.restfull.ecommerce.application.response.PaymentResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {

    @Transactional
    PaymentResponse savePayment(PaymentRequest request);

    @Transactional
    Page<PaymentResponse> getAllPagedPayments(int page, int size);

    @Transactional
    PaymentResponse findByIdPayment(Long id);
//
//    @Transactional
//    PaymentResponse updatePayment(PaymentRequest request);
//
//    @Transactional
//    void deletePayment(Long id);
}
