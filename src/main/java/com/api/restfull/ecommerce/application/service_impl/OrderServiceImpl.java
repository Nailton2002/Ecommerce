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

        return null;
    }

    @Override
    public Page<OrderResponse> getAllPagedOrders(int page, int size) {
        return null;
    }

    @Override
    public OrderResponse findByIdOrder(Long id) {
        Order order = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado por id" + id));
        return null;
    }

    @Override
    public OrderResponse updateOrder(OrderRequest request) {
        return null;
    }

    @Override
    public void deleteOrder(Long orderId) {

    }

}
