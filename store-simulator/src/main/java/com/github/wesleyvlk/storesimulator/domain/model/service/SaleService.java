package com.github.wesleyvlk.storesimulator.domain.model.service;

import com.github.wesleyvlk.storesimulator.domain.model.Sale;

public interface SaleService {

    Sale addItemSaleToSale(Sale sale);

    Sale findSaleByCustomerId(Integer customerId);

}
