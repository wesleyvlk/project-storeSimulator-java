package com.github.wesleyvlk.storesimulator.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {

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

}
