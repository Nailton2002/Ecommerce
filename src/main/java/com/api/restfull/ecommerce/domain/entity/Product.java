package com.api.restfull.ecommerce.domain.entity;

import com.api.restfull.ecommerce.application.request.ProductRequest;
import com.api.restfull.ecommerce.application.response.ProductResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Produto")
@Table(name = "tb_produto")
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private BigDecimal preco;
    @JsonIgnore @ManyToOne @JoinColumn(name = "categoria_id")
    private Category categoria;
    @ManyToMany @JoinTable(name = "pedido_produto", joinColumns = @JoinColumn(name = "produto_id"), inverseJoinColumns = @JoinColumn(name = "pedido_id"))
    private List<Order> pedidos;
    private String descricao;
    private Integer quantidadeEstoque;
    private Boolean ativo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime dataCriacao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime dataUltimaAtualizacao;

    public Product(ProductRequest request){
        nome = request.nome();
        preco = request.preco();
        // Se categoriaId estiver presente no request, cria a associação com Categoria
        if (request.categoriaId() != null) {
            this.categoria = new Category();
            this.categoria.setId(request.categoriaId());
        }
        descricao = request.descricao();
        quantidadeEstoque = request.quantidadeEstoque();
        ativo = true;
    }

    public Product(ProductResponse response){
        id = response.id();
        nome = response.nome();
        preco = response.preco();
        if(response.categoria() != null){
            categoria = new Category();
            categoria.setId(response.categoria().id());
        }
        descricao = response.descricao();
        quantidadeEstoque = response.quantidadeEstoque();
        ativo = response.ativo();
        dataCriacao = response.dataCriacao() != null ? response.dataCriacao() : LocalDateTime.now();
        dataUltimaAtualizacao = response.dataUltimaAtualizacao() != null ? response.dataUltimaAtualizacao() : LocalDateTime.now();
    }

    public void updateProduct(ProductRequest request) {
        if (request.nome() != null) {
            this.nome = request.nome();
        }
        if (request.preco() != null){
            this.preco = request.preco();
        }
        if (request.descricao() != null) {
            this.descricao = request.descricao();
        }
        if (request.quantidadeEstoque() != null){
            this.quantidadeEstoque = request.quantidadeEstoque();
        }
        if (request.ativo() != null) {
            this.ativo = request.ativo();
        }
    }

    public void desableProduct() {
        this.ativo = false;
    }
}
