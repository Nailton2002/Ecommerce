package com.api.restfull.ecommerce.domain.entity;

import com.api.restfull.ecommerce.application.request.CategoryRequest;
import com.api.restfull.ecommerce.application.response.CategoryResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Categoria")
@Table(name = "tb_categoria")
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private boolean ativo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDate dataCriacao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDate dataUltimaAtualizacao;

    public static Category fromRequestToEntity(CategoryRequest request) {
        return Category.builder()
                .nome(request.getNome())
                .descricao(request.getDescricao())
                .build();
    }

    public static Category fromResponseToEntity(CategoryResponse response) {
        return Category.builder()
                .id(response.getId())
                .nome(response.getNome())
                .descricao(response.getDescricao())
                .ativo(response.isAtivo())
                .dataCriacao(response.getDataCriacao() != null ? response.getDataCriacao() : LocalDate.now())
                .dataUltimaAtualizacao(response.getDataUltimaAtualizacao() != null ? response.getDataUltimaAtualizacao() : LocalDate.now())
                .build();
    }

    public void updateCategory(CategoryRequest request){
        if (request.getNome() != null){
            this.nome = request.getNome();
        }
        if (request.getDescricao() != null){
            this.descricao = request.getDescricao();
        }
    }
}
