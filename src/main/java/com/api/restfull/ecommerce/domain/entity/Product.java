package com.api.restfull.ecommerce.domain.entity;

import com.api.restfull.ecommerce.application.request.ProductRequest;
import com.api.restfull.ecommerce.application.response.ProductResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Product")
@Table(name = "tb_product")
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer quantityStock;

    private Boolean active;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime creationDate;

    @JsonIgnore @ManyToOne @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToMany @JoinTable(name = "order_item_product", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "order_item_id"))
    private List<OrderItem> orderItems;

    public Product(ProductRequest request){
        id = request.id();
        name = request.name();
        price = request.price();
        description = request.description();
        quantityStock = request.quantityStock();
        active = true;
        // Se categoryId estiver presente no request, cria a associação com category
        if (request.categoryId() != null) {
            this.category = new Category();
            this.category.setId(request.categoryId());
        }
    }

    public void updateProduct(ProductRequest request) {
        if (request.name() != null) {
            this.name = request.name();
        }
        if (request.price() != null){
            this.price = request.price();
        }
        if (request.description() != null) {
            this.description = request.description();
        }
        if (request.quantityStock() != null){
            this.quantityStock = request.quantityStock();
        }
        if (request.active() != null) {
            this.active = request.active();
        }
    }

    public void desableProduct() {
        this.active = false;
    }
}
