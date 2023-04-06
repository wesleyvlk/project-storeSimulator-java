package com.github.wesleyvlk.storesimulator.controller;

import com.github.wesleyvlk.storesimulator.domain.model.Sale;
import com.github.wesleyvlk.storesimulator.domain.model.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    private SaleService saleService;


    @PostMapping("/{userId}/add-product")
    public ResponseEntity<Sale> addProductToSale(@PathVariable Integer userId, @RequestParam Integer productId, @RequestParam Integer productQuantity) {
        try {
            Sale sale = saleService.addProductToSale(userId, productId, productQuantity);
            return ResponseEntity.ok(sale);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/{userId}")
    public ResponseEntity<List<Sale>> getSalesByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(saleService.getSalesByUser(userId));
    }
}
