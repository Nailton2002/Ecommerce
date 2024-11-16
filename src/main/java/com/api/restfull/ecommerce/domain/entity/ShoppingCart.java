package com.api.restfull.ecommerce.domain.entity;

import com.api.restfull.ecommerce.domain.enums.StatusOrder;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Carrinho")
@Table(name = "tb_carrinho_compras")
public class ShoppingCart {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "cliente_id")
    private Client cliente;
    @ManyToMany @JoinTable(name = "carrinho_produto", joinColumns = @JoinColumn(name = "carrinho_id"), inverseJoinColumns = @JoinColumn(name = "produto_id"))
    private List<Product> produtos;
    // Valor total do carrinho (calculado com base nos itens)
    private Double total;
    @Column(name = "cancelada") @Enumerated(EnumType.STRING)
    private StatusOrder status;
    // Data de criação e última atualização do carrinho
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime dataCriacao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime dataUltimaAtualizacao;
}
