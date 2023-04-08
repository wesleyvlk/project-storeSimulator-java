package com.github.wesleyvlk.storesimulator.domain.repository;

import com.github.wesleyvlk.storesimulator.domain.model.Customer;
import com.github.wesleyvlk.storesimulator.domain.model.Sale;
import org.springframework.data.repository.CrudRepository;

public interface SaleRepository extends CrudRepository<Sale, Integer> {

    Sale findByCustomer(Customer customer);

}
