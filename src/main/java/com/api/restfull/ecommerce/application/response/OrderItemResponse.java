package com.api.restfull.ecommerce.application.response;

import com.api.restfull.ecommerce.domain.entity.OrderItem;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long id,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subsumOfItemsOfOrders,
        ProductResponse product,
        OrderResponse order
) {
    public OrderItemResponse(OrderItem orderItem) {
        this(
                orderItem.getId(),
                orderItem.getQuantity(),
                orderItem.getUnitPrice(),
                // Calcula subsumOfItemsOfOrders com base no unitPrice e quantity
                calculateSubsum(orderItem.getUnitPrice(), orderItem.getQuantity()),
                orderItem.getProduct() != null ? new ProductResponse(orderItem.getProduct()) : null,
                orderItem.getOrder() != null ? new OrderResponse(orderItem.getOrder()) : null
        );
    }
    /**
     * Método auxiliar para calcular o subsumOfItemsOfOrders.
     *
     * @param unitPrice o preço unitário do item
     * @param quantity  a quantidade do item
     * @return o total calculado (preço unitário * quantidade), ou null se um dos valores for nulo
     */
    private static BigDecimal calculateSubsum(BigDecimal unitPrice, Integer quantity) {
        if (unitPrice != null && quantity != null) {
            return unitPrice.multiply(BigDecimal.valueOf(quantity));
        }
        // Retorna 0 se valores forem inválidos
        return BigDecimal.ZERO;
    }
}
