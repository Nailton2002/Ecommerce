package com.api.restfull.ecommerce.application.service_impl;

import com.api.restfull.ecommerce.application.request.OrderRequest;
import com.api.restfull.ecommerce.application.response.OrderResponse;
import com.api.restfull.ecommerce.application.service.OrderService;
import com.api.restfull.ecommerce.domain.entity.Client;
import com.api.restfull.ecommerce.domain.entity.Order;
import com.api.restfull.ecommerce.domain.entity.Product;
import com.api.restfull.ecommerce.domain.enums.StatusOrder;
import com.api.restfull.ecommerce.domain.exception.BusinessRuleException;
import com.api.restfull.ecommerce.domain.exception.ResourceNotFoundException;
import com.api.restfull.ecommerce.domain.model.Address;
import com.api.restfull.ecommerce.domain.repository.ClientRepository;
import com.api.restfull.ecommerce.domain.repository.OrderRepository;
import com.api.restfull.ecommerce.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderResponse saveOrder(OrderRequest request) {
        // Instancia um novo pedido
        Order order = new Order();

        // Configura o endereço de entrega, se fornecido
        if (request.addressDelivery() != null) {
            order.setAddressDelivery(new Address(request.addressDelivery()));
        }

        // Busca e associa o cliente ao pedido
        Client client = clientRepository.findById(request.clientId()).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + request.clientId()));

        // Valida se o cliente está ativo
        if (!client.getActive()) {
            throw new BusinessRuleException("Cliente não está ativo");
        }
        order.setClient(client);

        // Valida se o statusOrder é válido e atribui
        if (request.statusOrder() != null) {
            try {
                order.setStatusOrder((request.statusOrder()));
            } catch (BusinessRuleException e) {
                throw new BusinessRuleException("Status inválido fornecido: " + request.statusOrder());
            }
        }

        // Busca e associa os produtos ao pedido com validações
        List<Product> products = request.productIds().stream()
                .map(productId -> productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + productId)))
                .peek(product -> {
                            // Valida se o produto está ativo
                            if (!product.getActive()) {
                                throw new ResourceNotFoundException("Produto com ID " + product.getId() + " não está ativo.");
                            }
                            // Valida se o produto tem estoque suficiente
                            if (product.getQuantityStock() <= 0) {
                                throw new ResourceNotFoundException("Produto com ID " + product.getId() + " não tem estoque suficiente.");
                            }
                        }
                ).collect(Collectors.toList());

        // Associa os produtos ao pedido
//        order.setProducts(products);

        // Salva o pedido no banco de dados
        Order savedOrder = repository.save(order);

        // Retorna o DTO de resposta
        return new OrderResponse(savedOrder);
    }

    @Override
    public Page<OrderResponse> getAllPagedOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> ordersPage = repository.findAll(pageable);
        return ordersPage.map(OrderResponse::new);
    }

    @Override
    public OrderResponse findByIdOrder(Long id) {
        Order order = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado por id" + id));
        return new OrderResponse(order);
    }

    @Override
    public OrderResponse updateOrder(OrderRequest request) {
        // Busca o pedido no banco de dados
        Order order = repository.findById(request.id()).orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + request.id()));

        // Verifica se o cliente está ativo
        Client client = clientRepository.findById(request.clientId()).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + request.clientId()));

        if (!client.getActive()) throw new BusinessRuleException("O cliente associado ao pedido está inativo.");

        // Verifica se todos os produtos estão ativos e possuem estoque suficiente
        List<Product> products = request.productIds().stream()
                .map(productId -> productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + productId)))
                .collect(Collectors.toList());

        for (Product product : products) {
            if (!product.getActive()) {
                throw new BusinessRuleException("Produto '" + product.getName() + "' está inativo.");
            }
            if (product.getQuantityStock() <= 0) {
                throw new BusinessRuleException("Produto '" + product.getName() + "' está sem estoque.");
            }
        }
        // Atualiza os dados do pedido
        order.updateOrder(request);
        // Salva as alterações no banco
        Order updatedOrder = repository.save(order);
        // Retorna o DTO de resposta
        return new OrderResponse(updatedOrder);
    }

    @Override
    public void deleteOrder(Long orderId) {

        // Verifica se o pedido existe
        Order order = repository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + orderId));

        // Verifica se o pedido pode ser excluído
        if (!order.getStatusOrder().equals(StatusOrder.PENDING) && !order.getStatusOrder().equals(StatusOrder.CANCELED)) {
            throw new BusinessRuleException("Pedidos entregues ou em andamento não podem ser excluídos.");
        }
        // Restitui o estoque dos produtos do pedido
//        for (Product product : order.getProducts()) {
//            product.setQuantityStock(product.getQuantityStock() + 1); // Ajuste a lógica de restituição conforme necessário
//            productRepository.save(product);
//        }
        // Registra log de exclusão (simulação)
        System.out.println("Pedido excluído: " + order.getId() + ", Data: " + LocalDateTime.now());

        // Exclui o pedido
        repository.delete(order);
    }


}
