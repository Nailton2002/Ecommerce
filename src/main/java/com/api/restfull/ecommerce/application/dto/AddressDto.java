package com.api.restfull.ecommerce.application.dto;

import com.api.restfull.ecommerce.domain.model.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressDto(

        @NotBlank
        String neighborhood,

        String complement,

        String country,

        @NotBlank
        String zipCode,

        @NotBlank
        String street,

        @NotBlank
        String number,

        @NotBlank
        String state,

        @NotBlank
        String city,

        String uf
) {

    public AddressDto(Address address) {
        this(
                address.getNeighborhood(),
                address.getComplement(),
                address.getCountry(),
                address.getZipCode(),
                address.getStreet(),
                address.getNumber(),
                address.getState(),
                address.getCity(),
                address.getUf());
    }
}
