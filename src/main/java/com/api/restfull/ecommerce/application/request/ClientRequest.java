package com.api.restfull.ecommerce.application.request;

import com.api.restfull.ecommerce.domain.model.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequest{
        @NotBlank(message = "O nome é obrigatório.")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
        @Column(nullable = false)
        private String nome;
        @Email(message = "Formato do email é inválido")
        @NotBlank(message = "Email é obrigatório")
        private String email;
        @CPF(message = "CPF inválido")
        @NotBlank(message = "CPF é obrigatório")
        private String cpf;
        @NotNull(message = "A data de nascimento é obrigatória.")
        @Past(message = "A data de nascimento deve estar no passado.")
        @JsonFormat(pattern = "dd/MM/yyyy")
        private LocalDate dataNascimento;
        @Column(nullable = false)
        @NotBlank(message = "Telefone é obrigatório")
        @Pattern(regexp = "\\d{10,11}", message = "O telefone deve conter 10 ou 11 dígitos.")
        private String telefone;
        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private boolean ativo;
        @Valid @NotNull(message = "Dados do endereço são obrigatórios")
        private Address endereco;


}
