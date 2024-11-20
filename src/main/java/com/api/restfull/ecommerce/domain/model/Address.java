package com.api.restfull.ecommerce.domain.model;

import com.api.restfull.ecommerce.application.dto.AddressDto;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String neighborhood;
    private String complement;
    private String country;
    private String zipCode;
    private String street;
    private String number;
    private String state;
    private String city;
    private String uf;

    public Address(AddressDto addressDto) {
        neighborhood = addressDto.neighborhood();
        complement = addressDto.complement();
        country = addressDto.country();
        zipCode = addressDto.zipCode();
        street = addressDto.street();
        number = addressDto.number();
        state = addressDto.state();
        city = addressDto.city();
        uf = addressDto.uf();
    }

    public void updateInformation(AddressDto dto) {
        if (dto.neighborhood() != null) {
            this.neighborhood = dto.neighborhood();
        }
        if (dto.complement() != null) {
            this.complement = dto.complement();
        }
        if (dto.country() != null) {
            this.country = dto.country();
        }
        if (dto.neighborhood() != null) {
            this.neighborhood = dto.neighborhood();
        }
        if (dto.zipCode() != null) {
            this.zipCode = dto.zipCode();
        }
        if (dto.street() != null) {
            this.street = dto.street();
        }
        if (dto.number() != null) {
            this.number = dto.number();
        }
        if (dto.state() != null) {
            this.state = dto.state();
        }
        if (dto.city() != null) {
            this.city = dto.city();
        }
        if (dto.uf() != null) {
            this.uf = dto.uf();
        }
    }
}
