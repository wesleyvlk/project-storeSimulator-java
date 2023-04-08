package com.github.wesleyvlk.storesimulator.domain.repository;

import com.github.wesleyvlk.storesimulator.domain.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Customer findByCpf(String cpf);

}
