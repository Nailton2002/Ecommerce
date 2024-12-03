package com.api.restfull.ecommerce.application.request.customer;

import com.api.restfull.ecommerce.application.dto.AddressDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record CustomerRequest(

        @Size(min = 3, max = 100, message = "O name deve ter entre 3 e 100 caracteres.")
        @NotBlank(message = "O name é obrigatório.")
        @Column(nullable = false)
        String name,

        @Email(message = "Formato do email é inválido")
        @NotBlank(message = "Email é obrigatório")
        String email,

        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}")
        @NotBlank(message = "CPF é obrigatório")
        @CPF(message = "CPF inválido")
        String cpf,

        @Past(message = "A data de nascimento deve estar no passado.")
        @NotNull(message = "A data de nascimento é obrigatória.")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dateOfBirth,

        @Pattern(regexp = "\\d{10,11}", message = "O telephone deve conter 10 ou 11 dígitos.")
        @NotBlank(message = "telephone é obrigatório")
        @Column(nullable = false)
        String telephone,

        @Column(nullable = false)
        Boolean active,

        @Valid @NotNull(message = "Dados do endereço são obrigatórios")
        AddressDto address) {


}

