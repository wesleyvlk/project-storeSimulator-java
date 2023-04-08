package com.github.wesleyvlk.storesimulator.controller;

import com.github.wesleyvlk.storesimulator.domain.model.Customer;
import com.github.wesleyvlk.storesimulator.domain.model.service.CustomerService;
import com.github.wesleyvlk.storesimulator.dto.CustomerDTO;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody @Valid CustomerDTO dto) {
        Customer customer = toPOJO(dto);
        customerService.createCustomer(customer);
        return ResponseEntity.ok(toDTO(customer));
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Customer> getCustomerByCpf(@PathVariable @Valid String cpf) {
        return ResponseEntity.ok(customerService.findCustomerByCpf(cpf));
    }

    private CustomerDTO toDTO(Customer customer) {
        return modelMapper.map(customer, CustomerDTO.class);
    }

    private Customer toPOJO(CustomerDTO dto) {
        return modelMapper.map(dto, Customer.class);
    }

}
