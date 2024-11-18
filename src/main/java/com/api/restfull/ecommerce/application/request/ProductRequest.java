package com.api.restfull.ecommerce.application.request;

import com.api.restfull.ecommerce.domain.entity.Product;

import java.math.BigDecimal;

public record ProductRequest(
        Long id,
        String nome,
        BigDecimal preco,
        Long categoriaId,
        String descricao,
        Integer quantidadeEstoque,
        Boolean ativo) {

    public ProductRequest(Product product) {
        this(
                product.getId(),
                product.getNome(),
                product.getPreco(),
                product.getCategoria() != null ? product.getCategoria().getId() : null,
                product.getDescricao(),
                product.getQuantidadeEstoque(),
                product.getAtivo()
        );
    }

}

