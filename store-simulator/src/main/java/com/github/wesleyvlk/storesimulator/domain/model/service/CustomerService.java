package com.github.wesleyvlk.storesimulator.domain.model.service;

import com.github.wesleyvlk.storesimulator.domain.model.Customer;

public interface CustomerService {

    Customer createCustomer(Customer customer);

    Customer findCustomerByCpf(String cpf);

}
