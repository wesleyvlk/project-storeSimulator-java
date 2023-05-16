package com.github.wesleyvlk.storesimulator.sale.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class SaleDTO {

    @NotNull(message = "Customer id is required")
    private UUID customerId;

    @JsonIgnore
    @NumberFormat(pattern = "%.2f")
    private BigDecimal totalPrice;

    @JsonIgnore
    private List<ItemSaleDTO> itemSales;

    @Data
    public static class ItemSaleDTO {

        private UUID productId;

        @NotNull(message = "Quantity is required")
        private Long quantity;

    }

}
