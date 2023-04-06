    package com.github.wesleyvlk.storesimulator.domain.model.service;

    import com.github.wesleyvlk.storesimulator.domain.model.ItemSale;
    import com.github.wesleyvlk.storesimulator.domain.model.Product;
    import com.github.wesleyvlk.storesimulator.domain.model.Sale;
    import com.github.wesleyvlk.storesimulator.domain.repository.ItemSaleRepository;
    import com.github.wesleyvlk.storesimulator.domain.repository.ProductRepository;
    import com.github.wesleyvlk.storesimulator.domain.repository.SaleRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    public class SaleServiceImpl implements SaleService {

        @Autowired
        private SaleRepository saleRepository;
        @Autowired
        private ItemSaleRepository itemSaleRepository;
        @Autowired
        private ProductRepository productRepository;


        @Override
        public Sale addProductToSale(Integer userId, Integer productId, Integer productQuantity) {
            Sale sale = saleRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            Product foundProduct = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));

            ItemSale itemSale = new ItemSale();
            itemSale.setProduct(foundProduct);
            itemSale.setQuantity(productQuantity);
            itemSale.setPrice(foundProduct.getPrice());
            itemSale.setSale(sale);

            itemSale = itemSaleRepository.save(itemSale);
            sale.getItemSales().add(itemSale);

            return saleRepository.save(sale);
        }

        @Override
        public List<Sale> getSalesByUser(Integer userId) {
            return saleRepository.findByUserId(userId);
        }
    }
