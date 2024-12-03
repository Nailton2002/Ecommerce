package com.api.restfull.ecommerce.application.request.category;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record CategoryRequest(

        @Size(max = 100, message = "O nome da categoria deve ter no máximo 100 caracteres")
        @NotBlank(message = "O nome da categoria é obrigatório")
        String name,

        @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
        @NotBlank(message = "A descrição da categoria é obrigatória.")
        String description,

        @Column(nullable = false)
        Boolean active,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
        LocalDateTime creationDate

) {

}
