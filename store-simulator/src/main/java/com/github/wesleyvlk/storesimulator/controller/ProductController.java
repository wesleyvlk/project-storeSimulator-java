package com.github.wesleyvlk.storesimulator.controller;

import com.github.wesleyvlk.storesimulator.domain.model.Product;
import com.github.wesleyvlk.storesimulator.domain.model.service.ProductService;
import com.github.wesleyvlk.storesimulator.dto.ProductDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductDTO dto) {
        Product product = toPOJO(dto);
        productService.createProduct(product);
        return ResponseEntity.ok(toDTO(product));
    }

    @GetMapping
    public @NotNull ResponseEntity<Iterable<Product>> getProducts() {
        return ResponseEntity.ok(productService.findAllProducts());
    }

    private ProductDTO toDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    private Product toPOJO(ProductDTO dto) {
        return modelMapper.map(dto, Product.class);
    }

}
