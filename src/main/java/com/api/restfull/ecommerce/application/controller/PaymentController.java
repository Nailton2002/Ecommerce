package com.api.restfull.ecommerce.application.controller;

import com.api.restfull.ecommerce.application.request.CreditCardPaymentRequest;
import com.api.restfull.ecommerce.application.request.DebitCardPaymentRequest;
import com.api.restfull.ecommerce.application.request.OrderItemRequest;
import com.api.restfull.ecommerce.application.request.PaymentRequest;
import com.api.restfull.ecommerce.application.response.OrderItemResponse;
import com.api.restfull.ecommerce.application.response.PaymentResponse;
import com.api.restfull.ecommerce.application.service.PaymentService;
import com.api.restfull.ecommerce.domain.exception.ExceptionLogger;
import com.api.restfull.ecommerce.domain.exception.ResourceNotFoundExceptionLogger;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService service;
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @PostMapping("/credit-card")
    public ResponseEntity<PaymentResponse> processCreditCardPayment(@Valid @RequestBody CreditCardPaymentRequest request) {

        long startTime = System.currentTimeMillis();
        logger.info("Recebendo requisição: [method=POST, endpoint=/payments/credit-card, body={}]", request);

        try {
            PaymentResponse response = service.processCreditCardPayment(request);

            long executionTime = System.currentTimeMillis() - startTime;
            logger.info("Resposta enviada: [status=201, body={}, executionTime={}ms]", response, executionTime);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.warn("Recurso não encontrado: [status=404, message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (ExceptionLogger ex) {
            logger.error("Erro ao processar o Pagamento: {}", ex.getMessage(), ex);
            throw ex;

        } catch (Exception ex) {
            logger.error("Erro inesperado ao processar pagamento: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @PostMapping("/credit-debit")
    public ResponseEntity<PaymentResponse> processDebitCardPayment(@Valid @RequestBody DebitCardPaymentRequest request) {

        long startTime = System.currentTimeMillis();
        logger.info("Recebendo requisição: [method=GET, endpoint=/payments/credit-debit, body={}]", request);

        try {
            PaymentResponse response = service.processDebitCardPayment(request);

            long executionTime = System.currentTimeMillis() - startTime;
            logger.info("Resposta enviada: [status=201, body={}, executionTime={}ms]", response, executionTime);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.warn("Recurso não encontrado: [status=404, message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado ao processar pagamento com cartão de débito: [message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (Exception ex) {
            logger.error("Erro genérico inesperado: [message={}]", ex.getMessage(), ex);
            throw new RuntimeException("Erro ao processar a solicitação.", ex);
        }
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<PaymentResponse> findByIdOrderItem(@PathVariable Long id) {
//        logger.info("Recebendo requisição para buscar Item do Pedido pelo ID: [method=GET, endpoint=/order-items, body={}] {}", id);
//        try {
//            logger.info("Requisição concluída com sucesso: Item encontrado ID={}", service.findByIdPayment(id).id());
//            return ResponseEntity.ok(service.findByIdPayment(id));
//        } catch (ResourceNotFoundExceptionLogger ex) {
//            logger.error("Erro ao buscar payment:  [status=200, body={}", ex.getMessage(), ex);
//            throw ex;
//        } catch (ExceptionLogger ex) {
//            logger.error("Erro inesperado ao buscar payment: {}", ex.getMessage(), ex);
//            throw ex;
//        }
//    }
}
