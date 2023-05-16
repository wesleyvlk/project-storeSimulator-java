package com.github.wesleyvlk.storesimulator.sale.domain.model.service;

import com.github.wesleyvlk.storesimulator.sale.domain.model.Sale;
import com.github.wesleyvlk.storesimulator.sale.domain.repository.SaleRepository;
import com.github.wesleyvlk.storesimulator.shared.service.SharedServiceFindAll;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class SaleServiceImpl implements SharedServiceFindAll<Sale> {

    private final SaleRepository repository;

    @Override
    @Transactional
    public Sale create(Sale sale) {
        return repository.save(sale);
    }

    @Override
    public Iterable<Sale> findAll() {
        return repository.findAllByOrderByCustomerNameAscTotalPriceAsc();
    }

    @Override
    public Optional<Sale> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Sale update(Sale sale) {
        return repository.save(sale);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        repository.deleteById(id);
    }

}
