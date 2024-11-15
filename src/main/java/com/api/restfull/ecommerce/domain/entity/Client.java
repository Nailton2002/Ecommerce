package com.api.restfull.ecommerce.domain.entity;

import com.api.restfull.ecommerce.domain.model.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Cliente")
@Table(name = "tb_cliente")
public class Client {
    // Identificador único
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Informações pessoais
    private String nome;
    private String email;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;
    private Address endereco;
    // Status do cliente (ativo, inativo, etc.)
    private boolean ativo;
    // Dados de registro (podem ser definidos automaticamente)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDate dataCadastro;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDate dataUltimaAtualizacao;
    @OneToMany(mappedBy = "cliente")
    private List<Order> pedidos;
}
