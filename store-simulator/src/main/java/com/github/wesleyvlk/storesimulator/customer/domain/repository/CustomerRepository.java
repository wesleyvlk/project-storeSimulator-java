package com.github.wesleyvlk.storesimulator.customer.domain.repository;

import com.github.wesleyvlk.storesimulator.customer.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Iterable<Customer> findAllByOrderByNameAsc();

}
