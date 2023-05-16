package com.github.wesleyvlk.storesimulator.product.controller;

import com.github.wesleyvlk.storesimulator.product.domain.model.Product;
import com.github.wesleyvlk.storesimulator.product.dto.ProductDTO;
import com.github.wesleyvlk.storesimulator.shared.controller.SharedController;
import com.github.wesleyvlk.storesimulator.shared.service.SharedServiceFindAll;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController implements SharedController<Product, ProductDTO> {

    private final SharedServiceFindAll<Product> service;

    @Override
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid ProductDTO dto) {
        Product createdProduct = Product.builder().build();
        BeanUtils.copyProperties(dto, createdProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(createdProduct));
    }

    @GetMapping
    public ResponseEntity<Iterable<Product>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @Override
    @GetMapping("/{product_id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "product_id") UUID productId) {
        Product foundProductById = foundRuleOfThree(productId, service);
        return ResponseEntity.status(HttpStatus.OK).body(foundProductById);
    }

    @Override
    @PutMapping("/{product_id}")
    public ResponseEntity<Object> update(@PathVariable(value = "product_id") UUID productId, @RequestBody @Valid ProductDTO dto) {
        Product updatedFoundProduct = foundRuleOfThree(productId, service);
        updatedFoundProduct = Product.builder().id(updatedFoundProduct.getId()).build();
        BeanUtils.copyProperties(dto, updatedFoundProduct);
        return ResponseEntity.status(HttpStatus.OK).body(service.update(updatedFoundProduct));
    }

    @Override
    @DeleteMapping("/{product_id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "product_id") UUID productId) {
        Product deletedFoundProduct = foundRuleOfThree(productId, service);
        service.delete(deletedFoundProduct.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Product deleted successfully!");
    }

}
