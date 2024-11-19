package com.api.restfull.ecommerce.domain.model;

import com.api.restfull.ecommerce.application.dto.AdressDto;
import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String complement;
    private String publicPace;
    private String neighborhood;
    private String number;
    private String city;
    private String cep;
    private String uf;

    public void updateInformation(AdressDto dto) {
        if (dto.complement() != null) {
            this.complement = dto.complement();
        }
        if (dto.publicPace() != null) {
            this.publicPace = dto.publicPace();
        }
        if (dto.neighborhood() != null) {
            this.neighborhood = dto.neighborhood();
        }
        if (dto.cep() != null) {
            this.cep = dto.cep();
        }
        if (dto.number() != null) {
            this.number = dto.number();
        }
        if (dto.city() != null) {
            this.city = dto.city();
        }
        if (dto.uf() != null) {
            this.uf = dto.uf();
        }
    }
}
