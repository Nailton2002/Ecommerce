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
        logger.info("Iniciando a criação do pagamento. Detalhes da requisição: {}", request);
        try {
            // Validações básicas da requisição
            if (request == null) {
                logger.error("A requisição de pagamento está nula.");
                throw new IllegalArgumentException("Requisição de pagamento inválida.");
            }
            // Lógica para salvar
            Payment payment = new Payment(request);

            //Busca o pedido por id que extraido pelo do objeto request
            payment.setOrder(orderRepository.findById(request.orderId())
            .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + request.orderId())));

            // Salvar a entidade no repositório
            Payment savedPayment = repository.save(payment);

            // Gerar um log informativo
            logger.info("Pagamento salvo com sucesso. ID: {}, Pedido: {}, Valor: {}", savedPayment.getId(), savedPayment.getOrder().getId(), savedPayment.getValue());

            // Retornar o DTO de resposta
            return new PaymentResponse(savedPayment);
        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.error("Erro ao buscar entidades relacionadas: {}", ex.getMessage(), ex);
            throw ex;
        } catch (ExceptionLogger ex) {
            logger.error("Erro ao salvar o pagamento: {}", ex.getMessage(), ex);
            throw new RuntimeException("Erro inesperado ao criar o pagamento.");
        }
    }

    @Override
    public Page<PaymentResponse> getAllPagedPayments(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Payment> orderItemPage = repository.findAll(pageable);
        return orderItemPage.map(PaymentResponse::new);
    }

    @Override
    public PaymentResponse findByIdPayment(Long id) {
        logger.info("Buscando Pagamento pelo ID: {}", id);
        try {
            Payment payment = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Item do Pedido não encontrado por id: " + id));
            logger.info("Pagamento encontrado: ID={}, Quantidade={}", payment.getId(), payment);
            return new PaymentResponse(payment);
        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.error("Erro ao buscar Pagamento: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

//    @Override
//    public PaymentResponse updatePayment(PaymentRequest request) {
//        logger.info("Iniciando atualização do Pagamento ID: {}", request.id());
//        try {
//            logger.debug("Pagamento encontrado para atualização: {}", );
//
//            logger.info("Pagamento atualizado com sucesso: ID={}", );
//
//            return new PaymentResponse();
//        } catch (ExceptionLogger ex) {
//            logger.error("Erro ao atualizar Pagamento: {}", ex.getMessage(), ex);
//            throw ex;
//        }
//    }
//
//    @Override
//    public void deletePayment(Long id) {
//        logger.info("Iniciando exclusão do Pagamento ID: {}", id);
//        try {
//            // Valida a existência do OrderItem pelo ID
//
//            // Obtém o pedido associado ao OrderItem
//
//            // Verifica se o pedido pode ser excluído
//            if (!order.getStatusOrder().equals(StatusOrder.PENDING) && !order.getStatusOrder().equals(StatusOrder.CANCELED)) {
//                logger.warn("Tentativa de excluir pedido com status inválido: Status={}", order.getStatusOrder());
//                throw new BusinessRuleException("Pedidos entregues ou em andamento não podem ser excluídos.");
//            }
//            // Restitui o estoque do produto associado ao item
//
//            // Exclui o pedido
//            repository.delete();
//            logger.info("Pagamento excluído com sucesso: ID={}, Pedido={}, Produto={}, Data={}", LocalDateTime.now());
//        } catch (
//                ExceptionLogger ex) {
//            logger.error("Erro ao excluir Pagamentoo: {}", ex.getMessage(), ex);
//            throw ex;
//        }
//
//    }

}
