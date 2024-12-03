package com.api.restfull.ecommerce.application.response.customer;

import com.api.restfull.ecommerce.application.dto.AddressDto;
import com.api.restfull.ecommerce.domain.entity.Customer;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CustomerListResponse(

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

    public CustomerListResponse(Customer customer) {
        this(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getCpf(),
                customer.getDateOfBirth(),
                customer.getTelephone(),
                customer.getActive(),
                customer.getRegistrationDate() != null ? customer.getRegistrationDate() : LocalDateTime.now(),
                customer.getAddress() != null ? new AddressDto(customer.getAddress()) : null

        );
    }
}
