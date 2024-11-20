package com.api.restfull.ecommerce.domain.repository;

import com.api.restfull.ecommerce.domain.entity.Category;
import com.api.restfull.ecommerce.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE LOWER(c.name) = LOWER(:name)")
    Optional<Category> findByName(@Param("name") String name);

    @Query(value = "SELECT c FROM Category c WHERE c.description = :description")
    List<Category> findByDescription(@Param("description") String description);

    @Query("SELECT p FROM Category p WHERE p.active = :active")
    List<Category> findByActives(@Param("active") Boolean active);

}
