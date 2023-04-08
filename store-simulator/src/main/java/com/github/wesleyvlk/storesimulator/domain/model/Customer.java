package com.github.wesleyvlk.storesimulator.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "customer")
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotNull(message = "Customer name is required.")
    @Size(min = 3, message = "name should be at least 3 chars", max = 50)
    private String name;

    @NotNull
    @Size(min = 11, max = 11)
    @Column(unique = true)
    private String cpf;

    @NotNull
    @Email
    @Size(max = 254)
    private String email;

    private LocalDateTime dateTime = LocalDateTime.now();

}
