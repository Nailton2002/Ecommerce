package com.api.restfull.ecommerce.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "OrderItem")
@Table(name = "tb_order_item")
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "order_id")
    private Order order;
    // product associado ao item do order
    @ManyToOne @JoinColumn(name = "product_id")
    private Product product;
    // quantity do product no order
    private Integer quantity;
    // Preço unitário do product no momento da compra
    private BigDecimal unitPrice;
    // SubsumOfItemsOfOrders calculado (preço unitário * quantity)
    private BigDecimal subsumOfItemsOfOrders;
}
