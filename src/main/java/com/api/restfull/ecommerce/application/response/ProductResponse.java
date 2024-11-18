package com.api.restfull.ecommerce.application.response;

import com.api.restfull.ecommerce.domain.entity.Product;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(
        Long id,
        String nome,
        // Preço unitário do produto
        BigDecimal preco,
        CategoryResponse categoria,

//      List<OrderResponse> pedidos,

        // Descrição detalhada do produto
        String descricao,
        // Quantidade disponível em estoque
        Integer quantidadeEstoque,
        // Status do produto (ativo/inativo)
        Boolean ativo,
        // Datas de criação e última atualização do produto
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
        LocalDateTime dataCriacao,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
        LocalDateTime dataUltimaAtualizacao) {

    // Construtor que converte uma entidade `Product` para o DTO
    public ProductResponse(Product product) {
        this(
                product.getId(),
                product.getNome(),
                product.getPreco(),
                product.getCategoria() != null ? new CategoryResponse(product.getCategoria()) : null,
                product.getDescricao(),
                product.getQuantidadeEstoque(),
                product.getAtivo(),
                product.getDataCriacao() != null ? product.getDataCriacao() : LocalDateTime.now(),
                product.getDataUltimaAtualizacao() != null ? product.getDataUltimaAtualizacao() : LocalDateTime.now()
        );
    }
}
