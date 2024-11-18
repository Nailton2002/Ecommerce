package com.api.restfull.ecommerce.application.request;

import com.api.restfull.ecommerce.application.dto.AdressDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record ClientRequest(@NotBlank(message = "O nome é obrigatório.")
                            @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
                            @Column(nullable = false)
                            String nome,
                            @Email(message = "Formato do email é inválido")
                            @NotBlank(message = "Email é obrigatório")
                            String email,
                            @CPF(message = "CPF inválido")
                            @NotBlank(message = "CPF é obrigatório")
                            @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}")
                            String cpf,
                            @NotNull(message = "A data de nascimento é obrigatória.")
                            @Past(message = "A data de nascimento deve estar no passado.")
                            @JsonFormat(pattern = "dd/MM/yyyy")
                            LocalDate dataNascimento,
                            @Column(nullable = false)
                            @NotBlank(message = "Telefone é obrigatório")
                            @Pattern(regexp = "\\d{10,11}", message = "O telefone deve conter 10 ou 11 dígitos.")
                            String telefone,
                            @Enumerated(EnumType.STRING)
                            @Column(nullable = false)
                            boolean ativo,
                            @Valid @NotNull(message = "Dados do endereço são obrigatórios")
                            AdressDto endereco) {


}

