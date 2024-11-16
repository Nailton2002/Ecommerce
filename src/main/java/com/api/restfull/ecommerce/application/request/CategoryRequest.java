package com.api.restfull.ecommerce.application.request;

import com.api.restfull.ecommerce.domain.entity.Category;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {

    private String nome;
    // Descrição opcional da categoria
    private String descricao;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private boolean ativo;

    public static CategoryRequest fromEntityToRequest (Category category) {
        return CategoryRequest.builder()
                .nome(category.getNome())
                .descricao(category.getDescricao())
                .ativo(category.isAtivo())
                .build();
    }
}
