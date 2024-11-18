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
@Entity(name = "ItemPedido")
@Table(name = "tb_item_pedido")
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "pedido_id")
    private Order pedido;
    // Produto associado ao item do pedido
    @ManyToOne @JoinColumn(name = "produto_id")
    private Product produto;
    // Quantidade do produto no pedido
    private Integer quantidade;
    // Preço unitário do produto no momento da compra
    private BigDecimal precoUnitario;
    // Subtotal calculado (preço unitário * quantidade)
    private BigDecimal subtotal;
}
