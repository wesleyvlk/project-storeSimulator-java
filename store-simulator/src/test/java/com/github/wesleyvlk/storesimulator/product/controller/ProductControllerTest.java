package com.github.wesleyvlk.storesimulator.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wesleyvlk.storesimulator.product.domain.model.Product;
import com.github.wesleyvlk.storesimulator.product.dto.ProductDTO;
import com.github.wesleyvlk.storesimulator.shared.service.SharedServiceFindAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SharedServiceFindAll<Product> productService;

    private ObjectMapper mapper;
    private Product product;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        product = Product.builder()
                .id(UUID.randomUUID())
                .name("Product 0")
                .price(BigDecimal.valueOf(10.00))
                .quantity(10L)
                .build();
    }

    @Test
    void testCreate() throws Exception {
        when(productService.create(any(Product.class))).thenReturn(product);
        ProductDTO dto = new ProductDTO();
        BeanUtils.copyProperties(product, dto);
        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(dto)))
                .andDo(print()).andReturn();
        verify(productService, times(1)).create(any(Product.class));
    }

    @Test
    void testFindAll() throws Exception {
        Product product1 = Product.builder()
                .id(UUID.randomUUID())
                .name("Product 1")
                .price(BigDecimal.valueOf(20.00))
                .quantity(20L)
                .build();
        Iterable<Product> products = Arrays.asList(product, product1);
        when(productService.findAll()).thenReturn(products);
        mockMvc.perform(get("/products"))
                .andDo(print()).andReturn();
        verify(productService, times(1)).findAll();
    }

    @Test
    void testFindById() throws Exception {
        when(productService.findById(any(UUID.class))).thenReturn(Optional.ofNullable(product));
        mockMvc.perform(get("/products/{product_id}", product.getId()))
                .andDo(print()).andReturn();
        verify(productService, times(1)).findById(any(UUID.class));
    }

    @Test
    void testUpdate() throws Exception {
        when(productService.findById(any(UUID.class))).thenReturn(Optional.ofNullable(product));
        ProductDTO dto = new ProductDTO();
        dto.setName("Product 0 Updated");
        dto.setPrice(BigDecimal.valueOf(10.00));
        dto.setQuantity(10L);

        product = Product.builder().id(product.getId()).build();
        BeanUtils.copyProperties(dto, product);
        when(productService.update(any(Product.class))).thenReturn(product);
        mockMvc.perform(put("/products/{product_id}", product.getId())
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(dto)))
                .andDo(print()).andReturn();
        verify(productService, times(1)).update(any(Product.class));
    }

    @Test
    void testDelete() throws Exception {
        when(productService.findById(any(UUID.class))).thenReturn(Optional.ofNullable(product));
        mockMvc.perform(delete("/products/{product_id}", product.getId()))
                .andDo(print()).andReturn();
        verify(productService, times(1)).delete(any(UUID.class));
    }
}