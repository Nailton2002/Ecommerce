package com.api.restfull.ecommerce.application.service_impl;

import com.api.restfull.ecommerce.application.request.PaymentRequest;
import com.api.restfull.ecommerce.application.response.PaymentResponse;
import com.api.restfull.ecommerce.application.service.PaymentService;
import com.api.restfull.ecommerce.domain.entity.Order;
import com.api.restfull.ecommerce.domain.entity.Payment;
import com.api.restfull.ecommerce.domain.enums.StatusOrder;
import com.api.restfull.ecommerce.domain.exception.BusinessRuleException;
import com.api.restfull.ecommerce.domain.exception.ExceptionLogger;
import com.api.restfull.ecommerce.domain.exception.ResourceNotFoundException;
import com.api.restfull.ecommerce.domain.exception.ResourceNotFoundExceptionLogger;
import com.api.restfull.ecommerce.domain.repository.OrderRepository;
import com.api.restfull.ecommerce.domain.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;
    private final OrderRepository orderRepository;
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Override
    public PaymentResponse savePayment(PaymentRequest request) {
        return null;
    }

    @Override
    public Page<PaymentResponse> getAllPagedPayments(int page, int size) {
        return null;
    }

    @Override
    public PaymentResponse findByIdPayment(Long id) {
        return null;
    }


}
