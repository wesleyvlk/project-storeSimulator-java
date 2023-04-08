package com.github.wesleyvlk.storesimulator.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

    @NotNull(message = "Product name is required.")
    private String name;

    private Double price;

    private Integer quantity;
}

