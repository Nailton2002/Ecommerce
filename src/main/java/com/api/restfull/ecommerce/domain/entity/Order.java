package com.api.restfull.ecommerce.domain.entity;

import com.api.restfull.ecommerce.application.dto.AddressDto;
import com.api.restfull.ecommerce.application.request.OrderRequest;
import com.api.restfull.ecommerce.application.response.OrderResponse;
import com.api.restfull.ecommerce.domain.enums.StatusOrder;
import com.api.restfull.ecommerce.domain.exception.ResourceNotFoundException;
import com.api.restfull.ecommerce.domain.model.Address;
import com.api.restfull.ecommerce.domain.repository.ClientRepository;
import com.api.restfull.ecommerce.domain.repository.ProductRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Order")
@Table(name = "tb_order")
public class Order {

    // Identificador único do order
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // client associado ao order
    @ManyToOne @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    // Conjunto de itens do order
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    // sumOfItemsOfOrders calculado do order (cálculo dinâmico com base nos itens)
    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusOrder statusOrder;

    // Datas de criação e última atualização do order
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime creationDate;

    // Data prevista para entrega
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime expectedDeliveryDate;

    // Endereço de entrega
    private Address addressDelivery;

    // Construtor para mapear uma entidade OrderRequest para a entidade Order
    public Order(OrderRequest request) {
        // Criação do cliente diretamente com o ID, para não precissar usar outro construtor so com o ID
        if (request.clientId() != null) {
            this.client = new Client();
            this.client.setId(request.clientId());
        }

        this.statusOrder = StatusOrder.valueOf(request.statusOrder() != null ? String.valueOf(request.statusOrder()) : "Status não definido");
        this.creationDate = request.creationDate() != null ? request.creationDate() : LocalDateTime.now();
        this.expectedDeliveryDate = request.expectedDeliveryDate() != null ? request.expectedDeliveryDate() : LocalDateTime.now();
        this.addressDelivery = request.addressDelivery() != null ? new Address(request.addressDelivery()) : null;
    }

    public void updateOrder(OrderRequest request) {
//        if (request.statusOrder() != null) this.statusOrder = request.statusOrder();

    }

}

