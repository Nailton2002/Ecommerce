package com.api.restfull.ecommerce.application.response;

import com.api.restfull.ecommerce.application.dto.AddressDto;
import com.api.restfull.ecommerce.domain.entity.Client;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ClientResponse(
        Long id,
        String name,
        String email,
        String cpf,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dateOfBirth,
        String telephone,
        Boolean active,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy'T'HH:mm:ss'Z'", timezone = "GMT")
        LocalDateTime registrationDate,
        AddressDto address) {

    public ClientResponse(Client client) {
        this(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getCpf(),
                client.getDateOfBirth(),
                client.getTelephone(),
                client.getActive(),
                client.getRegistrationDate() != null ? client.getRegistrationDate() : LocalDateTime.now(),
                client.getAddress() != null ? new AddressDto(client.getAddress()) : null

        );
    }
}
