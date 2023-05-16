package com.github.wesleyvlk.storesimulator.product.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;

@Data
public class ProductDTO {

    @NotBlank(message = "Product name is required")
    @Size(min = 5, max = 254, message = "Product name must be between 5 and 254 characters")
    private String name;

    @DecimalMin(value = "0.00", message = "Product price is required")
    @NumberFormat(pattern = "%.2f")
    private BigDecimal price;

    @DecimalMin(value = "0", message = "Product quantity is required")
    private Long quantity;

}
