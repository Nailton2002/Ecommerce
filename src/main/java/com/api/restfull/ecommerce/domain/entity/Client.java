package com.api.restfull.ecommerce.domain.entity;

import com.api.restfull.ecommerce.domain.model.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "client")
@Table(name = "tb_client")
public class Client {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String cpf;
    private LocalDate dateOfBirth;
    private String telephone;
    @Embedded
    private Address address;
    private boolean active;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDate registrationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDate lastUpdateDate;
    @OneToMany(mappedBy = "client")
    private List<Order> order;

    public void clientDesactive() {
        this.active = false;
    }

    @PreUpdate @PrePersist
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
