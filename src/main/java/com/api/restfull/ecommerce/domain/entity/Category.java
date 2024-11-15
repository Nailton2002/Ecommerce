package com.api.restfull.ecommerce.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Categoria")
@Table(name = "tb_categoria")
public class Category {
    // Identificador único
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Nome da categoria
    private String nome;
    // Descrição opcional da categoria
    private String descricao;
    // Status ativo/inativo
    private boolean ativo;
    // Datas de criação e última atualização
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDate dataCriacao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDate dataUltimaAtualizacao;
}
