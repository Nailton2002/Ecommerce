package com.api.restfull.ecommerce.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AdressDto(@NotBlank
                        String publicPace,
                        @NotBlank
                        String neighborhood,
                        @NotBlank
                        @Pattern(regexp = "\\d{8}")
                        String cep,
                        @NotBlank
                        String city,
                        @NotBlank
                        String uf,
                        String complement,
                        String number) {
}
