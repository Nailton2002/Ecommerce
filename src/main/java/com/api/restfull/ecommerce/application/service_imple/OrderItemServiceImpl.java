package com.api.restfull.ecommerce.application.service_imple;

import com.api.restfull.ecommerce.application.request.OrderItemRequest;
import com.api.restfull.ecommerce.application.request.OrderRequest;
import com.api.restfull.ecommerce.application.response.OrderItemResponse;
import com.api.restfull.ecommerce.application.service.OrderItemService;
import com.api.restfull.ecommerce.domain.entity.Order;
import com.api.restfull.ecommerce.domain.entity.OrderItem;
import com.api.restfull.ecommerce.domain.entity.Product;
import com.api.restfull.ecommerce.domain.exception.ResourceNotFoundException;
import com.api.restfull.ecommerce.domain.repository.OrderItemRepository;
import com.api.restfull.ecommerce.domain.repository.OrderRepository;
import com.api.restfull.ecommerce.domain.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository repository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderItemResponse saveOrderItem(OrderItemRequest request) {

        OrderItem orderItem = new OrderItem(request);

        Order order = orderRepository.findById(request.orderId()).orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + request.orderId()));
        orderItem.setOrder(order);

        Product product = productRepository.findById(request.productId()).orElseThrow(()-> new ResourceNotFoundException("Produto não encontrado com ID: " + request.productId()));
        orderItem.setProduct(product);

        return new OrderItemResponse(repository.save(orderItem));
    }

    @Override
    public Page<OrderItemResponse> getAllPagedOrderItems(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderItem> orderItemPage = repository.findAll(pageable);
        return orderItemPage.map(OrderItemResponse::new);
    }

    @Override
    public OrderItemResponse findByIdOrderItem(Long id) {
        return null;
    }

    @Override
    public OrderItemResponse updateOrderItem(OrderRequest request) {
        return null;
    }

    @Override
    public void deleteOrderItem(Long orderId) {

    }
}
