package com.api.restfull.ecommerce.domain.entity;

import com.api.restfull.ecommerce.application.request.OrderRequest;
import com.api.restfull.ecommerce.domain.enums.StatusOrder;
import com.api.restfull.ecommerce.domain.model.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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

    // customer associado ao order
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // Conjunto de itens do order
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

    // sumOfItemsOfOrders calculado do order (cálculo dinâmico com base nos itens)
    @Column(nullable = false)
    private BigDecimal total;

    // Datas de criação e última atualização do order
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime creationDate;

    //    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private StatusOrder statusOrder;

//    // Data prevista para entrega
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
//    private LocalDateTime expectedDeliveryDate;
//
//    // Endereço de entrega
//    private Address addressDelivery;


    public Order(Customer customer, List<OrderItem> items) {
        this.customer = customer;
        this.items = items;
        this.total = items.stream().map(OrderItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.creationDate = LocalDateTime.now();
    }

    // Construtor para mapear uma entidade OrderRequest para a entidade Order
//    public Order(OrderRequest request) {
//        // Criação do cliente diretamente com o ID, para não precissar usar outro construtor so com o ID
//        if (request.clientId() != null) {
//            this.customer = new Customer();
//            this.customer.setId(request.clientId());
//        }
//
////        this.statusOrder = StatusOrder.valueOf(request.statusOrder() != null ? String.valueOf(request.statusOrder()) : "Status não definido");
//        this.creationDate = request.creationDate() != null ? request.creationDate() : LocalDateTime.now();
//        this.expectedDeliveryDate = request.expectedDeliveryDate() != null ? request.expectedDeliveryDate() : LocalDateTime.now();
//        this.addressDelivery = request.addressDelivery() != null ? new Address(request.addressDelivery()) : null;
//    }


}

