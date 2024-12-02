package com.api.restfull.ecommerce.domain.entity;

import com.api.restfull.ecommerce.application.request.CartItemRequest;
import com.api.restfull.ecommerce.application.response.CartItemResponse;
import com.api.restfull.ecommerce.application.response.CartResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "CartItem")
@Table(name = "tb_cart_item")
public class CartItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    public CartItem(CartItemResponse response){
        
        if (response.productId() != null){
            this.product = new Product();
            this.product.setId(response.productId());
        }
        this.quantity = response.quantity();
        this.totalPrice = response.totalPrice();
        
    }

}
