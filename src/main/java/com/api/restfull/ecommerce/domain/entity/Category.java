package com.api.restfull.ecommerce.domain.entity;

import com.api.restfull.ecommerce.application.request.category.CategoryRequest;
import com.api.restfull.ecommerce.application.request.category.CategoryUpRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Category")
@Table(name = "tb_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String description;

    @Column
    private Boolean active;

    @Column
    private LocalDateTime creationDate;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Product> products;


    public Category(CategoryRequest request) {
        name = request.name();
        description = request.description();
        active = true;
        creationDate = LocalDateTime.now();
    }

    public void updateCategory(CategoryUpRequest request) {

        if (request.name() != null) this.name = request.name();

        if (request.description() != null) this.description = request.description();

        if (request.active() != null) this.active = request.active();

        if (request.updateDate() != null) this.creationDate = request.updateDate();
    }

    public void desableCategory() {
        this.active = false;
    }

}
