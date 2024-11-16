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

    private String complemento;
    private String logradouro;
    private String bairro;
    private String numero;
    private String cidade;
    private String cep;
    private String uf;

    public void updateInformation(AdressDto dto) {
        if (dto.complemento() != null) {
            this.complemento = dto.complemento();
        }
        if (dto.logradouro() != null) {
            this.logradouro = dto.logradouro();
        }
        if (dto.bairro() != null) {
            this.bairro = dto.bairro();
        }
        if (dto.cep() != null) {
            this.cep = dto.cep();
        }
        if (dto.numero() != null) {
            this.numero = dto.numero();
        }
        if (dto.cidade() != null) {
            this.cidade = dto.cidade();
        }
        if (dto.uf() != null) {
            this.uf = dto.uf();
        }
    }
}
