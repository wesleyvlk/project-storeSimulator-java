package com.github.wesleyvlk.storesimulator.domain.model.service;

import com.github.wesleyvlk.storesimulator.domain.model.Sale;

import java.util.List;

public interface SaleService {

    Sale addProductToSale(Integer userId, Integer productId, Integer productQuantity);

    List<Sale> getSalesByUser(Integer userId);
}
