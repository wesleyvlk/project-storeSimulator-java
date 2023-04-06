package com.github.wesleyvlk.storesimulator.domain.repository;

import com.github.wesleyvlk.storesimulator.domain.model.Sale;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SaleRepository extends CrudRepository<Sale, Integer> {
    List<Sale> findByUserId(Integer userId);
}
