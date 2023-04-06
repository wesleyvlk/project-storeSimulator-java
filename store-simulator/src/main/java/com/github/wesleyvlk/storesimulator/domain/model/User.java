package com.github.wesleyvlk.storesimulator.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "User name is required.")
    private String name;

    @NotNull(message = "User cpf is required.")
    private String cpf;

    @NotNull(message = "User email is required.")
    private String email;

}
