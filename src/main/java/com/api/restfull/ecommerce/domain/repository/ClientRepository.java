package com.api.restfull.ecommerce.domain.repository;

import com.api.restfull.ecommerce.domain.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Page<Client> findAll(Pageable pageable);

    @Query("SELECT c FROM Client c WHERE c.active = :active")
    List<Client> findByActives(@Param("active") Boolean active);

    @Query("SELECT c FROM Client c WHERE CAST(c.dateOfBirth AS string) LIKE CONCAT(:dateOfBirth, '%')")
    List<Client> findByDateOfBirthLike(@Param("dateOfBirth") String dateOfBirth);

    @Query("SELECT c FROM Client c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Client> findByNameContainingIgnoreCase(@Param("name") String name);

    @Query("SELECT c FROM Client c WHERE LOWER(c.email) LIKE LOWER(CONCAT('%', :email, '%'))")
    Optional<Client> findByEmail(@Param("email") String email);

    @Query("SELECT c FROM Client c WHERE c.cpf LIKE CONCAT(:cpf, '%')")
    Optional<Client> findByCpf(@Param("cpf") String cpf);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);
}
