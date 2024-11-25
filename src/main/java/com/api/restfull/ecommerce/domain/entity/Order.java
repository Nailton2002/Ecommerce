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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Order")
@Table(name = "tb_order")
public class Order {

    // Identificador único do order
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // client associado ao order
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToMany
    @JoinTable(name = "order_product", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;
    // Conjunto de itens do order
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItens;
    // Informações de payment
    @OneToMany(mappedBy = "order")
    private List<Payment> payment;
    @Column(name = "Pendente")
    @Enumerated(EnumType.STRING)
    private StatusOrder statusOrder;
    // sumOfItemsOfOrders calculado do order (cálculo dinâmico com base nos itens)
    private Double sumOfItemsOfOrders;
    // Datas de criação e última atualização do order
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime creationDate;
    // Data prevista para entrega
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime expectedDeliveryDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime lastUpdateDate;
    // Endereço de entrega
    private Address addressDelivery;

    // Construtor para mapear uma entidade OrderRequest para a entidade Order
    public Order(OrderRequest request) {
        // Criação do cliente diretamente com o ID, para não precissar usar outro construtor so com o ID
        if (request.clientId() != null) {
            this.client = new Client();
            this.client.setId(request.clientId());
        }
        // Criação da lista de produtos diretamente com os IDs
        if (request.productIds() != null) {
            this.products = request.productIds().stream().map(id -> {
                Product product = new Product();
                product.setId(id);
                return product;
            }).toList();
        } else {
            this.products = List.of();
        }
        this.statusOrder = StatusOrder.valueOf(request.statusOrder() != null ? String.valueOf(request.statusOrder()) : "Status não definido");
        this.creationDate = request.creationDate() != null ? request.creationDate() : LocalDateTime.now();
        this.expectedDeliveryDate = request.expectedDeliveryDate() != null ? request.expectedDeliveryDate() : LocalDateTime.now();
        this.addressDelivery = request.addressDelivery() != null ? new Address(request.addressDelivery()) : null;
    }

    public void updateOrder(OrderRequest request) {
        if (request.statusOrder() != null) this.statusOrder = request.statusOrder();
        if (request.lastUpdateDate() != null) this.lastUpdateDate = request.lastUpdateDate();
        if (request.sumOfItemsOfOrders() != null) this.sumOfItemsOfOrders = request.sumOfItemsOfOrders();
    }
}

