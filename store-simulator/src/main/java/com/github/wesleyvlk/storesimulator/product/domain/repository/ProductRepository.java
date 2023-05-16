package com.github.wesleyvlk.storesimulator.product.domain.repository;

import com.github.wesleyvlk.storesimulator.product.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Iterable<Product> findAllByOrderByNameAscPriceAsc();

}
