package com.github.wesleyvlk.storesimulator.domain.repository;

import com.github.wesleyvlk.storesimulator.domain.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {
}
