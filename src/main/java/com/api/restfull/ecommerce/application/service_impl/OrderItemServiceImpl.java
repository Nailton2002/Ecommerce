package com.api.restfull.ecommerce.application.service_impl;

import com.api.restfull.ecommerce.application.request.OrderItemRequest;
import com.api.restfull.ecommerce.application.response.OrderItemResponse;
import com.api.restfull.ecommerce.application.service.OrderItemService;
import com.api.restfull.ecommerce.domain.entity.Order;
import com.api.restfull.ecommerce.domain.entity.OrderItem;
import com.api.restfull.ecommerce.domain.entity.Product;
import com.api.restfull.ecommerce.domain.enums.StatusOrder;
import com.api.restfull.ecommerce.domain.exception.BusinessRuleException;
import com.api.restfull.ecommerce.domain.exception.ExceptionLogger;
import com.api.restfull.ecommerce.domain.exception.ResourceNotFoundException;
import com.api.restfull.ecommerce.domain.exception.ResourceNotFoundExceptionLogger;
import com.api.restfull.ecommerce.domain.repository.OrderItemRepository;
import com.api.restfull.ecommerce.domain.repository.OrderRepository;
import com.api.restfull.ecommerce.domain.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository repository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(OrderItemService.class);

    @Override
    public OrderItemResponse saveOrderItem(OrderItemRequest request) {
        logger.info("Iniciando salvamento de Item do Pedido para Pedido ID: {}, Produto ID: {}", request.orderId(), request.productId());
        try {
            // Lógica para salvar
            OrderItem orderItem = new OrderItem(request);

            Order order = orderRepository.findById(request.orderId()).orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + request.orderId()));
            orderItem.setOrder(order);

            Product product = productRepository.findById(request.productId()).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + request.productId()));
            orderItem.setProduct(product);

            OrderItem savedItem = repository.save(orderItem);

            logger.info("Item do Pedido salvo com sucesso: ID={}, Pedido={}, Produto={}", savedItem.getId(), order.getId(), product.getName());
            return new OrderItemResponse(savedItem);
        } catch (ExceptionLogger ex) {
            logger.error("Erro ao salvar Item do Pedido: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public Page<OrderItemResponse> getAllPagedOrderItems(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderItem> orderItemPage = repository.findAll(pageable);
        return orderItemPage.map(OrderItemResponse::new);
    }

    @Override
    public OrderItemResponse findByIdOrderItem(Long id) {
        logger.info("Buscando Item do Pedido pelo ID: {}", id);
        try {
            OrderItem orderItem = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Item do Pedido não encontrado por id: " + id));
            logger.info("Item do Pedido encontrado: ID={}, Quantidade={}", orderItem.getId(), orderItem.getQuantity());
            return new OrderItemResponse(orderItem);
        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.error("Erro ao buscar Item do Pedido: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public OrderItemResponse updateOrderItem(OrderItemRequest request) {
        logger.info("Iniciando atualização do Item do Pedido ID: {}", request.id());
        try {
            OrderItem orderItem = repository.findById(request.id()).orElseThrow(() -> new ResourceNotFoundException("Item do Pedido não encontrado com ID: " + request.id()));
            logger.debug("Item encontrado para atualização: {}", orderItem);

            Order order = orderRepository.findById(request.orderId()).orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + request.orderId()));
            orderItem.setOrder(order);

            Product product = productRepository.findById(request.productId()).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + request.productId()));
            orderItem.setProduct(product);

            orderItem.updateOrdemItem(request);
            OrderItem updatedItem = repository.save(orderItem);
            logger.info("Item do Pedido atualizado com sucesso: ID={}", updatedItem.getId());

            return new OrderItemResponse(updatedItem);
        } catch (ExceptionLogger ex) {
            logger.error("Erro ao atualizar Item do Pedido: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public void deleteOrderItem(Long id) {
        logger.info("Iniciando exclusão do Item do Pedido ID: {}", id);
        try {
            // Valida a existência do OrderItem pelo ID
            OrderItem orderItem = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Item do Pedido não encontrado com ID : " + id));
            // Obtém o pedido associado ao OrderItem
            Order order = orderItem.getOrder();
            // Verifica se o pedido pode ser excluído
            if (!order.getStatusOrder().equals(StatusOrder.PENDING) && !order.getStatusOrder().equals(StatusOrder.CANCELED)) {
                logger.warn("Tentativa de excluir pedido com status inválido: Status={}", order.getStatusOrder());
                throw new BusinessRuleException("Pedidos entregues ou em andamento não podem ser excluídos.");
            }
            // Restitui o estoque do produto associado ao item
            Product product = orderItem.getProduct();
            product.setQuantityStock(product.getQuantityStock() + orderItem.getQuantity());
            productRepository.save(product);
            // Exclui o pedido
            repository.delete(orderItem);
            logger.info("Item do Pedido excluído com sucesso: ID={}, Pedido={}, Produto={}, Data={}", orderItem.getId(), order.getId(), product.getName(), LocalDateTime.now());
        } catch (ExceptionLogger ex) {
            logger.error("Erro ao excluir Item do Pedido: {}", ex.getMessage(), ex);
            throw ex;
        }

    }
}
