package com.api.restfull.ecommerce.domain.entity;

import com.api.restfull.ecommerce.domain.enums.StatusOrder;
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
@Entity(name = "ShoppingCart")
@Table(name = "tb_shopping_cart")
public class ShoppingCart {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "client_id")
    private Client client;
    @ManyToMany @JoinTable(name = "cart_product", joinColumns = @JoinColumn(name = "cart_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;
    // value sumOfItemsOfOrders do cart (calculado com base nos itens)
    private Double sumOfItemsOfOrders;
    @Column(name = "cancelada") @Enumerated(EnumType.STRING)
    private StatusOrder statusOrder;
    // Data de criação e última atualização do cart
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime creationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime lastUpdateDate;
}
