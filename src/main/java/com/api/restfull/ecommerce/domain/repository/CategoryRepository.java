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

    @Query("SELECT c FROM Category c WHERE c.active = :active")
    List<Category> findByActives(@Param("active") Boolean active);

    @Query("SELECT c FROM Category c WHERE LOWER(c.name) = LOWER(:name)")
    List<Category> findByNameDescriptionActive(@Param("name") String name);

    @Query("SELECT COUNT(c) > 0 FROM Category c WHERE LOWER(c.name) = LOWER(:name) AND LOWER(c.description) = LOWER(:description) AND c.active = :active")
    boolean existsByNameAndDescriptionAndActive(@Param("name") String name, @Param("description") String description, @Param("active") Boolean active);


}
