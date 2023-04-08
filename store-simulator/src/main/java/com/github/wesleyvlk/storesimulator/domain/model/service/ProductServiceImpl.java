    package com.github.wesleyvlk.storesimulator.domain.model.service;

    import com.github.wesleyvlk.storesimulator.domain.model.Product;
    import com.github.wesleyvlk.storesimulator.domain.repository.ProductRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    @Service
    public class ProductServiceImpl implements ProductService {

        @Autowired
        private ProductRepository productRepository;
        @Override
        public Iterable<Product> findAllProducts() {
            return productRepository.findAll();
        }

        @Override
        public Product createProduct(Product product) {
            return productRepository.save(product);
        }
    }
