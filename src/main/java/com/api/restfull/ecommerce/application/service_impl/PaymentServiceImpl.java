package com.api.restfull.ecommerce.application.service_impl;

import com.api.restfull.ecommerce.application.request.CreditCardPaymentRequest;
import com.api.restfull.ecommerce.application.request.DebitCardPaymentRequest;
import com.api.restfull.ecommerce.application.request.PaymentRequest;
import com.api.restfull.ecommerce.application.response.CartResponse;
import com.api.restfull.ecommerce.application.response.PaymentResponse;
import com.api.restfull.ecommerce.application.service.PaymentService;
import com.api.restfull.ecommerce.domain.entity.Cart;
import com.api.restfull.ecommerce.domain.entity.Order;
import com.api.restfull.ecommerce.domain.entity.Payment;
import com.api.restfull.ecommerce.domain.enums.StatusOrder;
import com.api.restfull.ecommerce.domain.exception.*;
import com.api.restfull.ecommerce.domain.repository.CartRepository;
import com.api.restfull.ecommerce.domain.repository.OrderRepository;
import com.api.restfull.ecommerce.domain.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    //    private final PaymentRepository repository;
    private final CartRepository cartRepository;
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);


    @Override
    public PaymentResponse processCreditCardPayment(CreditCardPaymentRequest request) {

        logger.info("Iniciando processar pagamento com cartão de crédito: [name={}, categoryId={}]", request.cartId());

        try {

            // Recupera o carrinho com base no cartId
            Cart cart = cartRepository.findById(request.cartId()).orElseThrow(() -> new ResourceNotFoundException("Carrinho não encontrado"));

            // Verifique se os itens estão presentes no carrinho
            if (cart.getItems() == null || cart.getItems().isEmpty()) {
                throw new ResourceNotFoundException("Carrinho está vazio.");
            }

            // Calcula o valor total do carrinho
            BigDecimal totalAmount = cart.getItems().stream()
                    .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Gera um ID de transação único
            String transactionId = "TRX-" + UUID.randomUUID();

            // Monta a resposta do pagamento, incluindo o carrinho com seus itens
            CartResponse cartResponse = CartResponse.fromCartToResponse(cart);

            return PaymentResponse.fromCreditCard(totalAmount, transactionId, cartResponse, LocalDateTime.now());

        } catch (
                DataIntegrityViolationException ex) {
            logger.error("Erro de integridade ao processar pagamento com cartão de crédito: {}", ex.getMessage(), ex);
            throw new DataIntegrityValidationException("O nome do produto já existe.");

        } catch (BusinessRuleException ex) {
            logger.warn("Regra de negócio violada ao processar pagamento com cartão de crédito: {}", ex.getMessage(), ex);
            throw ex;

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado ao processar pagamento com cartão de crédito: {}", ex.getMessage(), ex);
            throw ex;

        } catch (Exception ex) {
            logger.error("Erro genérico ao processar pagamento com cartão de crédito: {}", ex.getMessage(), ex);
            throw new RuntimeException("Erro ao processar pagamento com cartão de crédito.", ex);
        }
    }

    @Override
    public PaymentResponse processDebitCardPayment(DebitCardPaymentRequest request) {

        logger.info("Iniciando processar pagamento com cartão de débito: [name={}, categoryId={}]", request);
        try {

            // Recupera o carrinho com base no cartId
            Cart cart = cartRepository.findById(request.cartId()).orElseThrow(() -> new ResourceNotFoundException("Carrinho não encontrado"));

            // Verifique se os itens estão presentes no carrinho
            if (cart.getItems() == null || cart.getItems().isEmpty()) {
                throw new ResourceNotFoundException("Carrinho está vazio.");
            }

            // Simulação de validação do cartão de débito
            if (!isValidDebitCard(request.cardNumber())) {
                throw new ResourceNotFoundException("Número do cartão de débito inválido.");
            }

            // Lógica de processamento de pagamento (simulação)
            boolean paymentSuccess = processPayment(request.amount());
            if (!paymentSuccess) {
                throw new ResourceNotFoundException("Falha ao processar pagamento com cartão de débito.");
            }

            // Calcula o valor total do carrinho
            BigDecimal totalAmount = cart.getItems().stream()
                    .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Gera um ID de transação único
            String transactionId = "TRX-DEBIT-" + UUID.randomUUID();

            // Monta a resposta do pagamento, incluindo o carrinho com seus itens
            CartResponse cartResponse = CartResponse.fromCartToResponse(cart);

            return PaymentResponse.fromDebitCard(request.amount(), transactionId, cartResponse, LocalDateTime.now());

        } catch (
                DataIntegrityViolationException ex) {
            logger.error("Erro de integridade ao processar pagamento com cartão de crédito: {}", ex.getMessage(), ex);
            throw new DataIntegrityValidationException("O nome do produto já existe.");

        } catch (BusinessRuleException ex) {
            logger.warn("Regra de negócio violada ao processar pagamento com cartão de débito: {}", ex.getMessage(), ex);
            throw ex;

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado ao processar pagamento com cartão de débito: {}", ex.getMessage(), ex);
            throw ex;

        } catch (Exception ex) {
            logger.error("Erro genérico ao processar pagamento com cartão de crédito: {}", ex.getMessage(), ex);
            throw new RuntimeException("Erro ao processar pagamento com cartão de débito.", ex);
        }
    }


    // Métodos auxiliares de validação e processamento (simulação)

    private boolean isValidDebitCard(String cardNumber) {
        // Simulação de validação do cartão de débito
        return cardNumber.length() == 16; // Exemplo simplificado
    }

    private boolean processPayment(BigDecimal amount) {
        // Simulação do processamento do pagamento
        return amount.compareTo(BigDecimal.ZERO) > 0; // Sucesso se o valor for maior que zero
    }
}
