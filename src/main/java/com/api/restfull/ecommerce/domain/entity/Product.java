package com.api.restfull.ecommerce.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Produto")
@Table(name = "tb_produto")
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    // Preço unitário do produto
    private BigDecimal preco;
    @JsonIgnore @ManyToOne @JoinColumn(name = "categoria_id")
    private Category categoria;
    @ManyToMany @JoinTable(name = "pedido_produto", joinColumns = @JoinColumn(name = "produto_id"), inverseJoinColumns = @JoinColumn(name = "pedido_id"))
    private List<Order> pedidos;
    // Descrição detalhada do produto
    private String descricao;
    // Quantidade disponível em estoque
    private Integer quantidadeEstoque;
    // Status do produto (ativo/inativo)
    private boolean ativo;
    // Datas de criação e última atualização do produto
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime dataCriacao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime dataUltimaAtualizacao;
}
