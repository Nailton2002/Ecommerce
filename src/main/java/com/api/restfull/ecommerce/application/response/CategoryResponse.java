package com.api.restfull.ecommerce.application.response;

import com.api.restfull.ecommerce.domain.entity.Category;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record CategoryResponse(
        Long id,
        String nome,
        String descricao,
        Boolean ativo,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
        LocalDateTime dataCriacao,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
        LocalDateTime dataUltimaAtualizacao) {

    public CategoryResponse(Category category) {
        this(category.getId(),
             category.getNome(),
             category.getDescricao(),
             category.getAtivo(),
             category.getDataCriacao() != null ? category.getDataCriacao() : LocalDateTime.now(),
             category.getDataUltimaAtualizacao() != null ? category.getDataUltimaAtualizacao() : LocalDateTime.now());
    }
}
