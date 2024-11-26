package com.api.restfull.ecommerce.domain.entity;

import com.api.restfull.ecommerce.application.request.OrderItemRequest;
import com.api.restfull.ecommerce.application.response.OrderItemResponse;
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

    public OrderItem(OrderItemRequest request) {
        id = request.id();
        if (request.orderId() != null) {
            this.order = new Order();
            this.order.setId(request.id());
        }
        if (request.productId() != null) {
            this.product = new Product();
            this.product.setId(request.id());
        }
        quantity = request.quantity();
        unitPrice = request.unitPrice();
        subsumOfItemsOfOrders = request.subsumOfItemsOfOrders();
        // Calcula o subtotal (evitando receber do request)
    }

    // Método para calcular o subtotal
    private BigDecimal calculateSubTotal() {
        return this.unitPrice != null && this.quantity != null
                ? this.unitPrice.multiply(BigDecimal.valueOf(this.quantity))
                : BigDecimal.ZERO;
    }
}
