package com.api.restfull.ecommerce.application.response;

import com.api.restfull.ecommerce.application.dto.AddressDto;
import com.api.restfull.ecommerce.domain.entity.Order;
import com.api.restfull.ecommerce.domain.enums.StatusOrder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record OrderResponse(
        Long id,
        ClientResponse client,
        List<ProductResponse> products,
        List<OrderItemResponse> orderItens,
        List<PaymentResponse> payments,
        StatusOrder statusOrder,
        Double sumOfItemsOfOrders,
        LocalDateTime creationDate,
        LocalDateTime expectedDeliveryDate,
        AddressDto addressDelivery
) {

    // Construtor para mapear uma entidade Order para o DTO
    public OrderResponse(Order order) {
        this(
                order.getId(),
                order.getClient() != null ? new ClientResponse(order.getClient()) : null,
                // Converte lista de produtos para ProductResponse
                order.getProducts() != null ? order.getProducts().stream().map(ProductResponse::new).collect(Collectors.toList()) : List.of(),
                // Converte lista de itens do pedido para OrderItemResponse
                order.getOrderItens() != null ? order.getOrderItens().stream().map(OrderItemResponse::new).collect(Collectors.toList()) : List.of(),
                // Converte lista de pagamentos para PaymentResponse
                order.getPayment() != null ? order.getPayment().stream().map(PaymentResponse::new).collect(Collectors.toList()) : List.of(),
                order.getStatusOrder(),
                order.getSumOfItemsOfOrders(),
                // Usa o valor da data de criação se disponível, senão o horário atual
                order.getCreationDate() != null ? order.getCreationDate() : LocalDateTime.now(),
                // Usa a data de entrega prevista se disponível, senão o horário atual
                order.getExpectedDeliveryDate() != null ? order.getExpectedDeliveryDate() : LocalDateTime.now(),
                // Mapeia o endereço de entrega para AddressDto
                order.getAddressDelivery() != null ? new AddressDto(order.getAddressDelivery()) : null
        );
    }

}
