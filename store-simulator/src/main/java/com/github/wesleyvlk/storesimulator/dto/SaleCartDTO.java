package com.github.wesleyvlk.storesimulator.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SaleCartDTO {

    @NotNull(message = "Customer ID is required.")
    private Integer customerId;

    @NotNull(message = "Item sales are required.")
    private List<ItemSaleDTO> itemSales;

}
