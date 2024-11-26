package com.api.restfull.ecommerce.domain.entity;

import com.api.restfull.ecommerce.application.response.OrderResponse;
import com.api.restfull.ecommerce.application.response.PaymentResponse;
import com.api.restfull.ecommerce.domain.enums.MethodPayment;
import com.api.restfull.ecommerce.domain.enums.StatusPayment;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Payment")
@Table(name = "tb_payment")
public class Payment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // order associado ao payment
    @ManyToOne @JoinColumn(name = "order_id")
    private Order order;
    // value sumOfItemsOfOrders do payment
    private BigDecimal value;
    // Status do payment (ex: PENDENTE, APROVADO, REJEITADO, CANCELADO)
    @Column(name = "cancelado")
    @Enumerated(EnumType.STRING)
    private StatusPayment statusPayment;
    // Método de payment (ex: CARTAO_CREDITO, BOLETO, PIX)
    @Column(name = "CARTAO_CREDITO")
    @Enumerated(EnumType.STRING)
    private MethodPayment methodPayment;
    // Número de transação (gerado pelo processador de payment)
    @Column(unique = true, nullable = false)
    private String numberTransaction;
    // Data de realização do payment
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime dataPayment;
    // Data de criação e última atualização do registro de payment
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime creationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime lastUpdateDate;

    // Número de transação (gerado pelo processador de payment)
    @PrePersist
    public void gerarnumberTransacao() {
        // Gera um UUID e o atribui ao campo numberTransacao antes de salvar
        this.numberTransaction = UUID.randomUUID().toString();
    }

    public BigDecimal calculateOrderTotal(List<OrderItem> items) {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : items) {
            total = total.add(calculateSubSum(item.getUnitPrice(), item.getQuantity()));
        }
        return total;
    }

    /**
     * Método auxiliar para calcular a soma parcial (subSum) dos itens.
     *
     * @param unitPrice o preço unitário do item
     * @param quantity  a quantidade do item
     * @return o total calculado (preço unitário * quantidade), ou BigDecimal.ZERO se um dos valores for nulo
     */
    private static BigDecimal calculateSubSum(BigDecimal unitPrice, Integer quantity) {
        if (unitPrice != null && quantity != null) {
            return unitPrice.multiply(BigDecimal.valueOf(quantity));
        }
        // Retorna 0 se valores forem inválidos
        return BigDecimal.ZERO;
    }

}
