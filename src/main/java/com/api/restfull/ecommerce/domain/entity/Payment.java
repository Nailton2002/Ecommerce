package com.api.restfull.ecommerce.domain.entity;

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
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Pagamento")
@Table(name = "tb_pagamento")
public class Payment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Pedido associado ao pagamento
    @ManyToOne @JoinColumn(name = "pedido_id")
    private Order pedido;
    // Valor total do pagamento
    private BigDecimal valor;
    // Status do pagamento (ex: PENDENTE, APROVADO, REJEITADO, CANCELADO)
    @Column(name = "cancelado") @Enumerated(EnumType.STRING)
    private StatusPayment status;
    // Método de pagamento (ex: CARTAO_CREDITO, BOLETO, PIX)
    @Column(name = "CARTAO_CREDITO") @Enumerated(EnumType.STRING)
    private MethodPayment metodo;
    // Número de transação (gerado pelo processador de pagamento)
    @Column(unique = true, nullable = false)
    private String numeroTransacao;
    // Data de realização do pagamento
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime dataPagamento;
    // Data de criação e última atualização do registro de pagamento
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime dataCriacao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime dataUltimaAtualizacao;

    // Número de transação (gerado pelo processador de pagamento)
    @PrePersist
    public void gerarNumeroTransacao() {
        // Gera um UUID e o atribui ao campo numeroTransacao antes de salvar
        this.numeroTransacao = UUID.randomUUID().toString();
    }
}
