package com.api.restfull.ecommerce.domain.repository;

import com.api.restfull.ecommerce.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Método para buscar categoria pelo nome
    Optional<Category> findByNome(String nome);

}
