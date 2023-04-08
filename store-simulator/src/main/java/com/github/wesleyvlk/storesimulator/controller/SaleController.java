package com.github.wesleyvlk.storesimulator.controller;

import com.github.wesleyvlk.storesimulator.domain.model.Sale;
import com.github.wesleyvlk.storesimulator.domain.model.service.SaleService;
import com.github.wesleyvlk.storesimulator.dto.SaleCartDTO;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sale-cart")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/item")
    public ResponseEntity<SaleCartDTO> addItemtoSale(@RequestParam @Valid SaleCartDTO saleCartDTO) {
        Sale sale = toPOJO(saleCartDTO);
        saleService.addItemSaleToSale(sale);
        return ResponseEntity.ok(toDTO(sale));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Sale> getSaleByCustomer(@PathVariable Integer customerId) {
        return ResponseEntity.ok(saleService.findSaleByCustomerId(customerId));
    }

    private SaleCartDTO toDTO(Sale sale) {
        return modelMapper.map(sale, SaleCartDTO.class);
    }

    private Sale toPOJO(SaleCartDTO dto) {
        return modelMapper.map(dto, Sale.class);
    }

}
