package com.api.restfull.ecommerce.domain.entity;

import com.api.restfull.ecommerce.application.request.CategoryRequest;
import com.api.restfull.ecommerce.application.response.CategoryResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Category")
@Table(name = "tb_category")
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Boolean active;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime creationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime lastUpdateDate;

    public Category(CategoryRequest request) {
       name = request.name();
       description = request.description();
       active = true;
    }

    public Category(CategoryResponse response) {
                id = response.id();
                name = response.name();
                active = response.active();
                description = response.description();
                creationDate = response.creationDate() != null ? response.creationDate() : LocalDateTime.now();
                lastUpdateDate = response.lastUpdateDate() != null ? response.lastUpdateDate() : LocalDateTime.now();
    }

    public void updateCategory(CategoryRequest request) {
        if (request.name() != null) {
            this.name = request.name();
        }
        if (request.description() != null) {
            this.description = request.description();
        }
        if (request.active() != null) {
            this.active = request.active();
        }
    }

    public void desableCategory() {
        this.active = false;
    }
}
