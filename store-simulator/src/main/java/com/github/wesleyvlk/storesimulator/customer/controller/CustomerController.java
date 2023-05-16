package com.github.wesleyvlk.storesimulator.customer.controller;

import com.github.wesleyvlk.storesimulator.customer.domain.model.Customer;
import com.github.wesleyvlk.storesimulator.customer.dto.CustomerDTO;
import com.github.wesleyvlk.storesimulator.shared.controller.SharedController;
import com.github.wesleyvlk.storesimulator.shared.service.SharedServiceFindAll;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController implements SharedController<Customer, CustomerDTO> {

    private final SharedServiceFindAll<Customer> service;

    @Override
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid CustomerDTO dto) {
        Customer createdCustomer = Customer.builder().build();
        BeanUtils.copyProperties(dto, createdCustomer);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(createdCustomer));
    }

    @GetMapping
    public ResponseEntity<Iterable<Customer>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @Override
    @GetMapping("/{customer_id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "customer_id") UUID customerId) {
        Customer foundCustomerById = foundRuleOfThree(customerId, service);
        return ResponseEntity.status(HttpStatus.OK).body(foundCustomerById);
    }

    @Override
    @PutMapping("/{customer_id}")
    public ResponseEntity<Object> update(@PathVariable(value = "customer_id") UUID customerId,
                                         @RequestBody @Valid CustomerDTO dto) {
        Customer updatedFoundCustomer = foundRuleOfThree(customerId, service);
        if (!dto.getCpf().equals(updatedFoundCustomer.getCpf()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "CPF cannot be changed");
        updatedFoundCustomer = Customer.builder()
                .id(updatedFoundCustomer.getId())
                .name(dto.getName())
                .cpf(updatedFoundCustomer.getCpf())
                .email(dto.getEmail())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(service.update(updatedFoundCustomer));
    }

    @Override
    @DeleteMapping("/{customer_id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "customer_id") UUID customerId) {
        Customer deletedFoundCustomer = foundRuleOfThree(customerId, service);
        service.delete(deletedFoundCustomer.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Customer deleted successfully!");
    }

}
