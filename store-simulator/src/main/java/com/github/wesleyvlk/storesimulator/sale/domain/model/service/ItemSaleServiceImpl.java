package com.github.wesleyvlk.storesimulator.sale.domain.model.service;

import com.github.wesleyvlk.storesimulator.sale.domain.model.Sale;
import com.github.wesleyvlk.storesimulator.sale.domain.repository.ItemSaleRepository;
import com.github.wesleyvlk.storesimulator.shared.service.SharedService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ItemSaleServiceImpl implements SharedService<Sale.ItemSale> {

    private final ItemSaleRepository repository;

    @Override
    public Sale.ItemSale create(Sale.ItemSale itemSale) {
        return repository.save(itemSale);
    }

    @Override
    public Optional<Sale.ItemSale> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Sale.ItemSale update(Sale.ItemSale itemSale) {
        return repository.save(itemSale);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

}
