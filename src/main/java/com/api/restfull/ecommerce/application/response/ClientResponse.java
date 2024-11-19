package com.api.restfull.ecommerce.application.response;

import com.api.restfull.ecommerce.application.dto.AdressDto;

import java.time.LocalDate;

public record ClientResponse(Long id,
                             String name,
                             String email,
                             String cpf,
                             LocalDate dateOfBirth,
                             String telephone,
                             Boolean active,
                             AdressDto address) {

}
