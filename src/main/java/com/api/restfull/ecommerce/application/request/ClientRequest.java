package com.api.restfull.ecommerce.application.request;

import com.api.restfull.ecommerce.application.dto.AddressDto;
import com.api.restfull.ecommerce.domain.entity.Client;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record ClientRequest(

        Long id,
        @NotBlank(message = "O name é obrigatório.")
        @Size(min = 3, max = 100, message = "O name deve ter entre 3 e 100 caracteres.")
        @Column(nullable = false)
        String name,
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
        LocalDate dateOfBirth,
        @Column(nullable = false)
        @NotBlank(message = "telephone é obrigatório")
        @Pattern(regexp = "\\d{10,11}", message = "O telephone deve conter 10 ou 11 dígitos.")
        String telephone,
        boolean active,
        @Valid @NotNull(message = "Dados do endereço são obrigatórios")
        AddressDto address) {

    public ClientRequest(Client client) {
        this(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getCpf(),
                client.getDateOfBirth(),
                client.getTelephone(),
                client.isActive(),
                client.getAddress() != null ? new AddressDto(client.getAddress()) : null
        );
    }
}

