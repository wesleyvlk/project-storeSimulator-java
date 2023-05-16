package com.github.wesleyvlk.storesimulator.customer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wesleyvlk.storesimulator.customer.domain.model.Customer;
import com.github.wesleyvlk.storesimulator.customer.dto.CustomerDTO;
import com.github.wesleyvlk.storesimulator.shared.service.SharedServiceFindAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SharedServiceFindAll<Customer> customerService;

    private ObjectMapper mapper;
    private Customer customer;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        customer = Customer.builder()
                .id(UUID.randomUUID())
                .name("Customer 0")
                .cpf("12345678910")
                .email("customer0@domain.com")
                .build();
    }

    @Test
    void testCreate() throws Exception {
        when(customerService.create(any(Customer.class))).thenReturn(customer);
        CustomerDTO dto = new CustomerDTO();
        BeanUtils.copyProperties(customer, dto);
        mockMvc.perform(post("/customers")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(dto)))
                .andDo(print()).andReturn();
        verify(customerService, times(1)).create(any(Customer.class));
    }

    @Test
    void testFindAll() throws Exception {
        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .name("Customer 1")
                .cpf("10987654321")
                .email("customer1@domain.com")
                .build();
        Iterable<Customer> customers = Arrays.asList(customer, customer1);
        when(customerService.findAll()).thenReturn(customers);
        mockMvc.perform(get("/customers"))
                .andDo(print()).andReturn();
        verify(customerService, times(1)).findAll();
    }

    @Test
    void testFindById() throws Exception {
        when(customerService.findById(any(UUID.class))).thenReturn(Optional.ofNullable(customer));
        mockMvc.perform(get("/customers/{customer_id}", customer.getId()))
                .andDo(print()).andReturn();
        verify(customerService, times(1)).findById(any(UUID.class));
    }

    @Test
    void testUpdate() throws Exception {
        when(customerService.findById(any(UUID.class))).thenReturn(Optional.ofNullable(customer));
        CustomerDTO dto = new CustomerDTO();
        dto.setName("Customer 0 Updated");
        dto.setCpf("12345678910");
        dto.setEmail("customer0Updated@domainUpdated.com");

        customer = Customer.builder().id(customer.getId()).build();
        BeanUtils.copyProperties(dto, customer);
        if (!dto.getCpf().equals(customer.getCpf())) customer.setCpf(customer.getCpf());

        when(customerService.update(any(Customer.class))).thenReturn(customer);
        mockMvc.perform(put("/customers/{customer_id}", customer.getId())
                .contentType("application/json")
                .content(mapper.writeValueAsString(dto))).andDo(print()).andReturn();
        verify(customerService, times(1)).findById(any(UUID.class));
        verify(customerService, times(1)).update(any(Customer.class));
    }

    @Test
    void testDelete() throws Exception {
        when(customerService.findById(any(UUID.class))).thenReturn(Optional.ofNullable(customer));
        mockMvc.perform(delete("/customers/{customer_id}", customer.getId()))
                .andDo(print()).andReturn();
        verify(customerService, times(1)).delete(any(UUID.class));
    }

}
