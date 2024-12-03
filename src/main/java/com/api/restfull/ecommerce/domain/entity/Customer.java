package com.api.restfull.ecommerce.domain.entity;

import com.api.restfull.ecommerce.application.request.customer.CustomerRequest;
import com.api.restfull.ecommerce.application.request.customer.CustomerUpRequest;
import com.api.restfull.ecommerce.domain.model.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Customer")
@Table(name = "tb_customer")
public class Customer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false, unique = false)
    private String telephone;

    @Column
    private Boolean active;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime registrationDate;

    @Embedded
    private Address address;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(mappedBy = "customer")
    private List<Order> order;

    public Customer(CustomerRequest request) {

        this.name = request.name();
        this.email = request.email();
        this.cpf = request.cpf();
        this.dateOfBirth = request.dateOfBirth();
        this.telephone = request.telephone();
        this.active = true;
        // Garante que o endereço só seja configurado se o request.address() não for nulo
        if (request.address() != null) {
            this.setAddress(new Address(request.address()));
        } else {
            throw new IllegalArgumentException("O endereço não pode ser nulo.");
        }
    }

    public void updateCustomer(CustomerUpRequest request) {

        if (request.name() != null) this.name = request.name();
        if (request.email() != null) this.email = request.email();
        if (request.cpf() != null) this.cpf = request.cpf();
        if (request.dateOfBirth() != null) this.dateOfBirth = request.dateOfBirth();
        if (request.telephone() != null) this.telephone = request.telephone();
        if (request.address() != null) this.address = new Address(request.address());
        if (request.active() != null) this.active = request.active();
    }

    public void customerDesactive() {
        this.active = false;
    }

    @PreUpdate
    @PrePersist
    private void formatartelephone() {
        if (telephone.length() == 10) {
            telephone = String.format("(%s) %s-%s",
                    telephone.substring(0, 2),
                    telephone.substring(2, 6),
                    telephone.substring(6, 10));
        } else if (telephone.length() == 11) {
            telephone = String.format("(%s) %s-%s",
                    telephone.substring(0, 2),
                    telephone.substring(2, 7),
                    telephone.substring(7, 11));
        }
    }
}
