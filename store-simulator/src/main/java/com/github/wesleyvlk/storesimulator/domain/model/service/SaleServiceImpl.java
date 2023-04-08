package com.github.wesleyvlk.storesimulator.domain.model.service;

import com.github.wesleyvlk.storesimulator.domain.model.Customer;
import com.github.wesleyvlk.storesimulator.domain.model.ItemSale;
import com.github.wesleyvlk.storesimulator.domain.model.Sale;
import com.github.wesleyvlk.storesimulator.domain.repository.CustomerRepository;
import com.github.wesleyvlk.storesimulator.domain.repository.SaleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ItemSaleService itemSaleService;

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Sale addItemSaleToSale(Sale sale) {
        ItemSale itemSale = new ItemSale();
        itemSale.setSale(sale);
        itemSaleService.addItemSale(itemSale);
        return saleRepository.save(sale);
    }

    public Sale findSaleByCustomerId(Integer customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        return saleRepository.findByCustomer(customer);
    }


}
