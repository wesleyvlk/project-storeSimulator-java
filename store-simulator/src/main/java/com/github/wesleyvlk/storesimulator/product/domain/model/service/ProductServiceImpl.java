package com.github.wesleyvlk.storesimulator.product.domain.model.service;

import com.github.wesleyvlk.storesimulator.product.domain.model.Product;
import com.github.wesleyvlk.storesimulator.product.domain.repository.ProductRepository;
import com.github.wesleyvlk.storesimulator.shared.service.SharedServiceFindAll;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@AllArgsConstructor
@Service
public class ProductServiceImpl implements SharedServiceFindAll<Product> {

    private final ProductRepository repository;

    @Override
    @Transactional
    public Product create(Product product) {
        return repository.save(product);
    }

    @Override
    public Iterable<Product> findAll() {
        return repository.findAllByOrderByNameAscPriceAsc();
    }

    @Override
    public Optional<Product> findById(UUID productId) {
        return repository.findById(productId);
    }

    @Override
    @Transactional
    public Product update(Product product) {
        return repository.save(product);
    }

    @Override
    @Transactional
    public void delete(UUID productId) {
        repository.deleteById(productId);
    }

}
