package com.github.wesleyvlk.storesimulator.sale.domain.repository;

import com.github.wesleyvlk.storesimulator.sale.domain.model.Sale;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemSaleRepository extends
        CrudRepository<Sale.ItemSale, UUID> {
}
