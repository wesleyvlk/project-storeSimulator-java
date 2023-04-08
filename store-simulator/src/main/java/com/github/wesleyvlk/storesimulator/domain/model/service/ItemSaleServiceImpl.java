package com.github.wesleyvlk.storesimulator.domain.model.service;

import com.github.wesleyvlk.storesimulator.domain.model.ItemSale;
import com.github.wesleyvlk.storesimulator.domain.model.Product;
import com.github.wesleyvlk.storesimulator.domain.repository.ItemSaleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ItemSaleServiceImpl implements ItemSaleService {

    @Autowired
    private ItemSaleRepository itemSaleRepository;

    @Autowired
    private ProductService productService;

    @Override
    @Transactional
    public ItemSale addItemSale(ItemSale itemSale) {
        Product product = itemSale.getProduct();
        if (product.getQuantity() < itemSale.getQuantity()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product over");

        product.setQuantity(product.getQuantity() - itemSale.getQuantity());
        productService.createProduct(product);
        itemSale.setPrice(product.getPrice() * itemSale.getQuantity());
        return itemSaleRepository.save(itemSale);
    }
}
