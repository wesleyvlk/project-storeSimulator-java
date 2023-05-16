package com.github.wesleyvlk.storesimulator.sale.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wesleyvlk.storesimulator.customer.domain.model.Customer;
import com.github.wesleyvlk.storesimulator.product.domain.model.Product;
import com.github.wesleyvlk.storesimulator.sale.domain.model.Sale;
import com.github.wesleyvlk.storesimulator.sale.dto.SaleDTO;
import com.github.wesleyvlk.storesimulator.shared.service.SharedService;
import com.github.wesleyvlk.storesimulator.shared.service.SharedServiceFindAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(SaleController.class)
class SaleControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SharedServiceFindAll<Sale> saleService;
    @MockBean
    private SharedService<Sale.ItemSale> itemSaleService;
    @MockBean
    private SharedService<Customer> customerService;
    @MockBean
    private SharedService<Product> productService;

    private ObjectMapper mapper;
    private Customer customer;
    private Product product;
    private Sale sale;
    private Sale.ItemSale itemSale;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        customer = Customer.builder()
                .id(UUID.randomUUID())
                .name("Customer 0")
                .cpf("12345678910")
                .email("customer0@domain.com")
                .build();
        product = Product.builder()
                .id(UUID.randomUUID())
                .name("Product 1")
                .price(BigDecimal.valueOf(10.00))
                .quantity(10L)
                .build();
        sale = Sale.builder()
                .id(UUID.randomUUID())
                .customer(customer)
                .totalPrice(BigDecimal.ZERO)
                .itemSales(new ArrayList<>())
                .build();
        itemSale = Sale.ItemSale.builder()
                .id(UUID.randomUUID())
                .product(product)
                .quantity(2L)
                .price(BigDecimal.ZERO)
                .sale(sale)
                .build();
    }

    @Test
    void testCreate() throws Exception {
        when(customerService.findById(any(UUID.class))).thenReturn(Optional.ofNullable(customer));
        when(saleService.create(any(Sale.class))).thenReturn(sale);
        SaleDTO dto = new SaleDTO();
        dto.setCustomerId(customer.getId());
        dto.setTotalPrice(sale.getTotalPrice());
        dto.setItemSales(new ArrayList<>());
        mockMvc.perform(post("/sales-carts")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(dto)))
                .andDo(print()).andReturn();
        verify(saleService, times(1)).create(any(Sale.class));
    }

    @Test
    void testFindAll() throws Exception {
        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .name("Customer 1")
                .cpf("10987654321")
                .email("customer1@domain.com")
                .build();
        Sale sale1 = Sale.builder()
                .id(UUID.randomUUID())
                .customer(customer1)
                .totalPrice(BigDecimal.ZERO)
                .itemSales(new ArrayList<>())
                .build();
        Iterable<Sale> sales = Arrays.asList(sale, sale1);
        when(saleService.findAll()).thenReturn(sales);
        mockMvc.perform(get("/sales-carts"))
                .andDo(print()).andReturn();
        verify(saleService, times(1)).findAll();
    }

    @Test
    void testFindById() throws Exception {
        when(saleService.findById(any(UUID.class))).thenReturn(Optional.ofNullable(sale));
        mockMvc.perform(get("/sales-carts/{sale_id}", sale.getId()))
                .andDo(print()).andReturn();
        verify(saleService, times(1)).findById(any(UUID.class));
    }

    @Test
    void testUpdate() throws Exception {
        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .name("Customer 1")
                .cpf("10987654321")
                .email("customer1@domain.com")
                .build();
        when(customerService.findById(any(UUID.class))).thenReturn(Optional.ofNullable(customer1));
        when(saleService.findById(any(UUID.class))).thenReturn(Optional.ofNullable(sale));
        SaleDTO dto = new SaleDTO();
        dto.setCustomerId(customer1.getId());
        dto.setTotalPrice(sale.getTotalPrice());
        dto.setItemSales(new ArrayList<>());

        sale = Sale.builder()
                .id(sale.getId())
                .customer(customer1)
                .totalPrice(dto.getTotalPrice())
                .itemSales(sale.getItemSales())
                .build();
        when(saleService.update(any(Sale.class))).thenReturn(sale);
        mockMvc.perform(put("/sales-carts/{sale_id}", sale.getId())
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(dto)))
                .andDo(print()).andReturn();
        verify(saleService, times(1)).update(any(Sale.class));
    }

    @Test
    void testDelete() throws Exception {
        when(saleService.findById(any(UUID.class))).thenReturn(Optional.ofNullable(sale));
        if (!sale.getItemSales().isEmpty()) {
            for (Sale.ItemSale itemSale : sale.getItemSales()) {
                when(productService.findById(any(UUID.class))).thenReturn(Optional.ofNullable(product));
                when(itemSaleService.findById(any(UUID.class))).thenReturn(Optional.ofNullable(itemSale));

                product.setQuantity(product.getQuantity() + itemSale.getQuantity());
                sale.setTotalPrice(sale.getTotalPrice().subtract(itemSale.getPrice()));
                mockMvc.perform(delete("/sales-carts/items/{item_id}", itemSale.getId()))
                        .andDo(print()).andReturn();
            }
            verify(itemSaleService, times(1)).delete(any(UUID.class));
        }
        mockMvc.perform(delete("/sales-carts/{sale_id}", sale.getId()))
                .andDo(print()).andReturn();
        verify(saleService, times(1)).delete(any(UUID.class));
    }

    @Test
    void testCreateItem() throws Exception {
        when(saleService.findById(any(UUID.class))).thenReturn(Optional.ofNullable(sale));
        when(productService.findById(any(UUID.class))).thenReturn(Optional.ofNullable(product));
        when(itemSaleService.create(any(Sale.ItemSale.class))).thenReturn(itemSale);
        SaleDTO.ItemSaleDTO itemDto = new SaleDTO.ItemSaleDTO();
        itemDto.setProductId(product.getId());
        itemDto.setQuantity(itemSale.getQuantity());
        itemSale.setPrice(product.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity())));
        mockMvc.perform(post("/sales-carts/{sale_id}/items", sale.getId())
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(itemDto)))
                .andDo(print()).andReturn();
        verify(itemSaleService, times(1)).create(any(Sale.ItemSale.class));
    }

    @Test
    void testFindItemById() throws Exception {
        when(itemSaleService.findById(any(UUID.class))).thenReturn(Optional.ofNullable(itemSale));
        mockMvc.perform(get("/sales-carts/items/{item_id}", itemSale.getId()))
                .andDo(print()).andReturn();
    }

    @Test
    void testUpdateItem() throws Exception {
        when(productService.findById(any(UUID.class))).thenReturn(Optional.ofNullable(product));
        when(saleService.findById(any(UUID.class))).thenReturn(Optional.ofNullable(sale));
        when(itemSaleService.findById(any(UUID.class))).thenReturn(Optional.ofNullable(itemSale));
        SaleDTO.ItemSaleDTO itemDto = new SaleDTO.ItemSaleDTO();
        itemDto.setProductId(product.getId());
        itemDto.setQuantity(3L);

        itemSale = Sale.ItemSale.builder()
                .id(itemSale.getId())
                .product(product)
                .quantity(itemDto.getQuantity())
                .price(product.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity())))
                .sale(sale)
                .build();
        when(itemSaleService.update(any(Sale.ItemSale.class))).thenReturn(itemSale);
        mockMvc.perform(put("/sales-carts/items/{item_id}", itemSale.getId())
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(itemDto)))
                .andDo(print()).andReturn();
        verify(itemSaleService, times(1)).update(any(Sale.ItemSale.class));
    }

    @Test
    void testDeleteItem() throws Exception {
        when(productService.findById(any(UUID.class))).thenReturn(Optional.ofNullable(product));
        when(saleService.findById(any(UUID.class))).thenReturn(Optional.ofNullable(sale));
        when(itemSaleService.findById(any(UUID.class))).thenReturn(Optional.ofNullable(itemSale));

        product.setQuantity(product.getQuantity() + itemSale.getQuantity());
        sale.setTotalPrice(sale.getTotalPrice().subtract(itemSale.getPrice()));
        mockMvc.perform(delete("/sales-carts/items/{item_id}", itemSale.getId()))
                .andDo(print()).andReturn();
        verify(itemSaleService, times(1)).delete(any(UUID.class));
    }

}
