package com.api.restfull.ecommerce.domain.repository;

import com.api.restfull.ecommerce.domain.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Page<Customer> findAll(Pageable pageable);

    @Query("SELECT c FROM Customer c WHERE c.active = :active")
    List<Customer> findByActives(@Param("active") Boolean active);

    @Query("SELECT c FROM Customer c WHERE CAST(c.dateOfBirth AS string) LIKE CONCAT(:dateOfBirth, '%')")
    List<Customer> findByDateOfBirthLike(@Param("dateOfBirth") String dateOfBirth);

    @Query("SELECT c FROM Customer c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Customer> findByNameContainingIgnoreCase(@Param("name") String name);

    @Query("SELECT c FROM Customer c WHERE LOWER(c.email) LIKE LOWER(CONCAT('%', :email, '%'))")
    Optional<Customer> findByEmail(@Param("email") String email);

    @Query("SELECT c FROM Customer c WHERE c.cpf LIKE CONCAT(:cpf, '%')")
    Optional<Customer> findByCpf(@Param("cpf") String cpf);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);
}
