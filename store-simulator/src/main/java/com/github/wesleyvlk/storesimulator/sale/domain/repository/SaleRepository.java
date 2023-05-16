package com.github.wesleyvlk.storesimulator.sale.domain.repository;

import com.github.wesleyvlk.storesimulator.sale.domain.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SaleRepository extends JpaRepository<Sale, UUID> {
    Iterable<Sale> findAllByOrderByCustomerNameAscTotalPriceAsc();

}
