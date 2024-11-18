package com.api.restfull.ecommerce.application.request;

import com.api.restfull.ecommerce.domain.entity.Category;
import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
        @NotBlank
        String nome,
        @NotBlank
        String descricao,
        Boolean ativo
) {
    public CategoryRequest(Category category) {
        this(category.getNome(), category.getDescricao(), category.getAtivo());
    }

}
