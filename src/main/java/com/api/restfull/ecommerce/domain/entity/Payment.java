package com.api.restfull.ecommerce.domain.entity;

import com.api.restfull.ecommerce.domain.enums.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Payment")
@Table(name = "tb_payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod method;

    // Apenas para cartão
    private String cardNumber;

    // Apenas para cartão
    private String cardHolderName;

    // Apenas para cartão
    private String cardExpiration;

    // Apenas para PIX
    private String pixKey;

    // Para todos (CPF/CNPJ)
    private String document;

    @Column(nullable = false)
    private BigDecimal amount;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime paymentDate = LocalDateTime.now();

}
