package com.api.restfull.ecommerce.domain.repository;

import com.api.restfull.ecommerce.domain.entity.Category;
import com.api.restfull.ecommerce.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Categoria c WHERE LOWER(c.nome) = LOWER(:nome)")
    Optional<Category> findByName(@Param("nome") String nome);

    @Query(value = "SELECT c FROM Categoria c WHERE c.descricao = :descricao")
    List<Category> findByDescription(@Param("descricao") String descricao);

    @Query("SELECT p FROM Categoria p WHERE p.ativo = :ativo")
    List<Category> findByActives(@Param("ativo") Boolean ativo);

}
