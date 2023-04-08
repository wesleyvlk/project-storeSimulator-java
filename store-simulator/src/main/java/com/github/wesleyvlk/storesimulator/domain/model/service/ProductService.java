package com.github.wesleyvlk.storesimulator.domain.model.service;

import com.github.wesleyvlk.storesimulator.domain.model.Product;

public interface ProductService {
    Product createProduct(Product product);

    Iterable<Product> findAllProducts();

}
