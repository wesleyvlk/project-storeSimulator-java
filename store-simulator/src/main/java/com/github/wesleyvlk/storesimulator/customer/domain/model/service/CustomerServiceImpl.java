package com.github.wesleyvlk.storesimulator.customer.domain.model.service;

import com.github.wesleyvlk.storesimulator.customer.domain.model.Customer;
import com.github.wesleyvlk.storesimulator.customer.domain.repository.CustomerRepository;
import com.github.wesleyvlk.storesimulator.shared.service.SharedServiceFindAll;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements SharedServiceFindAll<Customer> {

    private final CustomerRepository repository;

    @Override
    @Transactional
    public Customer create(Customer customer) {
        return repository.save(customer);
    }

    @Override
    public Iterable<Customer> findAll() {
        return repository.findAllByOrderByNameAsc();
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Customer update(Customer customer) {
        return repository.save(customer);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        repository.deleteById(id);
    }

}
