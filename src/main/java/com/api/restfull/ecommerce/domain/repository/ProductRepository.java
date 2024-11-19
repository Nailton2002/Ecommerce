package com.api.restfull.ecommerce.domain.repository;

import com.api.restfull.ecommerce.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT obj FROM product obj WHERE obj.category.id = :id_cat ORDER BY name")
    List<Product> findByCategory(@Param(value = "id_cat") Long id_cat);

    @Query("SELECT p FROM product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Product> findByNameContainingIgnoreCase(@Param("name") String name);

    @Query("SELECT p FROM product p WHERE LOWER(p.name) = LOWER(:name)")
    Optional<Product> findByName(@Param("name") String name);

    @Query(value = "SELECT p FROM product p WHERE p.description = :description")
    List<Product> findByDescription(@Param("description") String description);

    @Query(value = "SELECT p FROM product p WHERE p.quantityStock = :quantityStock")
    List<Product> findByQuantity(@Param("quantityStock") Integer quantityStock);

    @Query("SELECT p FROM product p WHERE p.active = :active")
    List<Product> findByActives(@Param("active") Boolean active);

    Page<Product> findAll(Pageable pageable);
}
