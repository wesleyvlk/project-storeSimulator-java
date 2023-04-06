package com.github.wesleyvlk.storesimulator.domain.model.service;

import com.github.wesleyvlk.storesimulator.domain.model.Product;

public interface ProductService {

    Iterable<Product> getAllProducts();

    Product getProduct(Integer id);

    Product save(Product product);
}
