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

    @Query("SELECT obj FROM Produto obj WHERE obj.categoria.id = :id_cat ORDER BY nome")
    List<Product> findByCategory(@Param(value = "id_cat") Long id_cat);

    @Query("SELECT p FROM Produto p WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Product> findByNameContainingIgnoreCase(@Param("nome") String nome);

    @Query("SELECT p FROM Produto p WHERE LOWER(p.nome) = LOWER(:nome)")
    Optional<Product> findByName(@Param("nome") String nome);

    @Query(value = "SELECT p FROM Produto p WHERE p.descricao = :descricao")
    List<Product> findByDescription(@Param("descricao") String descricao);

    @Query(value = "SELECT p FROM Produto p WHERE p.quantidadeEstoque = :quantidadeEstoque")
    List<Product> findByQuantity(@Param("quantidadeEstoque") Integer quantidadeEstoque);

    @Query("SELECT p FROM Produto p WHERE p.ativo = :ativo")
    List<Product> findByAtivo(@Param("ativo") Boolean ativo);

    Page<Product> findAll(Pageable pageable);
}
