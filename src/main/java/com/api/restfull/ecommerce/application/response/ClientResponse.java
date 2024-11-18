package com.api.restfull.ecommerce.application.response;

import com.api.restfull.ecommerce.application.dto.AdressDto;

import java.time.LocalDate;

public record ClientResponse(Long id,
                             String nome,
                             String email,
                             String cpf,
                             LocalDate dataNascimento,
                             String telefone,
                             Boolean ativo,
                             AdressDto endereco) {

}
