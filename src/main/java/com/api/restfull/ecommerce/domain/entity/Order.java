package com.api.restfull.ecommerce.domain.entity;

import com.api.restfull.ecommerce.domain.enums.StatusOrder;
import com.api.restfull.ecommerce.domain.model.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
    @ManyToOne @JoinColumn(name = "client_id")
    private Client client;
    @ManyToMany @JoinTable(name = "order_product", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;
    // Conjunto de itens do order
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItens;
    // Informações de payment
    @OneToMany(mappedBy = "order")
    private List<Payment> payment;
    // Status do order (ex: PENDENTE, APROVADO, CANCELADO, FINALIZADO)
    @Column(name = "cancelada") @Enumerated(EnumType.STRING)
    private StatusOrder statusOrder;
    // sumOfItemsOfOrders calculado do order (cálculo dinâmico com base nos itens)
    private Double sumOfItemsOfOrders;
    // Datas de criação e última atualização do order
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime creationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime expectedDeliveryDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime lastUpdateDate;
    // Endereço de entrega
    private Address addressEntrega;
    // Data prevista para entrega

}
