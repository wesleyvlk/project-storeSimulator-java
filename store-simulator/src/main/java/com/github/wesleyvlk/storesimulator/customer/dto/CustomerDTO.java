package com.github.wesleyvlk.storesimulator.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDTO {

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, message = "name should be at least 3 chars", max = 50)
    private String name;

    @NotBlank(message = "CPF cannot be blank")
    @Size(min = 11, max = 11, message = "CPF should be 11 chars")
    private String cpf;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email")
    private String email;

}
