package com.github.wesleyvlk.storesimulator;

import com.github.wesleyvlk.storesimulator.domain.model.Product;
import com.github.wesleyvlk.storesimulator.domain.model.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StoreSimulatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreSimulatorApplication.class, args);
    }

}
