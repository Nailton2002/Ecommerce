package com.api.restfull.ecommerce.domain.entity;

import com.api.restfull.ecommerce.application.request.CategoryRequest;
import com.api.restfull.ecommerce.application.response.CategoryResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Categoria")
@Table(name = "tb_categoria")
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private Boolean ativo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime dataCriacao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime dataUltimaAtualizacao;

    public Category(CategoryRequest request) {
       nome = request.nome();
       descricao = request.descricao();
       ativo = true;
    }

    public Category(CategoryResponse response) {
                id = response.id();
                nome = response.nome();
                ativo = response.ativo();
                descricao = response.descricao();
                dataCriacao = response.dataCriacao() != null ? response.dataCriacao() : LocalDateTime.now();
                dataUltimaAtualizacao = response.dataUltimaAtualizacao() != null ? response.dataUltimaAtualizacao() : LocalDateTime.now();
    }

    public void updateCategory(CategoryRequest request) {
        if (request.nome() != null) {
            this.nome = request.nome();
        }
        if (request.descricao() != null) {
            this.descricao = request.descricao();
        }
        if (request.ativo() != null) {
            this.ativo = request.ativo();
        }
    }

    public void desableCategory() {
        this.ativo = false;
    }
}
