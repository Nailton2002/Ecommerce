package com.api.restfull.ecommerce.application.response.customer;

import com.api.restfull.ecommerce.application.dto.AddressDto;
import com.api.restfull.ecommerce.domain.entity.Customer;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CustomerResponse(
        Long id,
        String name,
        String email,
        String cpf,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dateOfBirth,
        String telephone,
        Boolean active
) {

    public CustomerResponse(Customer customer) {
        this(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getCpf(),
                customer.getDateOfBirth(),
                customer.getTelephone(),
                customer.getActive()

        );
    }
}
