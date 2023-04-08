package com.github.wesleyvlk.storesimulator.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotNull(message = "Product name is required.")
    private String name;

    private Double price;
    private Integer quantity;

    private LocalDateTime dateTime = LocalDateTime.now();

}
