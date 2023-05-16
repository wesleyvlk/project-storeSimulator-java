package com.github.wesleyvlk.storesimulator.sale.controller;

import com.github.wesleyvlk.storesimulator.customer.domain.model.Customer;
import com.github.wesleyvlk.storesimulator.product.domain.model.Product;
import com.github.wesleyvlk.storesimulator.sale.domain.model.Sale;
import com.github.wesleyvlk.storesimulator.sale.dto.SaleDTO;
import com.github.wesleyvlk.storesimulator.shared.controller.SharedController;
import com.github.wesleyvlk.storesimulator.shared.service.SharedService;
import com.github.wesleyvlk.storesimulator.shared.service.SharedServiceFindAll;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/sales-carts")
public class SaleController implements SharedController<Sale, SaleDTO> {

    private final SharedServiceFindAll<Sale> service;
    private SharedService<Sale.ItemSale> itemService;
    private SharedService<Customer> customerService;
    private SharedService<Product> productService;

    @Override
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid SaleDTO dto) {
        BigDecimal discountOrAddition = dto.getTotalPrice();
        Customer foundCustomer = foundRuleOfThree(dto.getCustomerId(), customerService);
        if (dto.getTotalPrice() == null) discountOrAddition = BigDecimal.ZERO;

        Sale createdSale = Sale.builder()
                .customer(foundCustomer)
                .totalPrice(discountOrAddition)
                .itemSales(new ArrayList<>())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(createdSale));
    }

    @GetMapping
    public ResponseEntity<Iterable<Sale>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @Override
    @GetMapping("/{sale_id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "sale_id") UUID saleId) {
        Sale foundSaleById = foundRuleOfThree(saleId, service);
        return ResponseEntity.status(HttpStatus.OK).body(foundSaleById);
    }

    @Override
    @PutMapping("/{sale_id}")
    public ResponseEntity<Object> update(@PathVariable(value = "sale_id") UUID saleId,
                                         @RequestBody @Valid SaleDTO dto) {
        BigDecimal discountOrAddition = dto.getTotalPrice();
        Sale updatedFoundSale = foundRuleOfThree(saleId, service);
        Customer foundCustomer = foundRuleOfThree(dto.getCustomerId(), customerService);
        updatedFoundSale = Sale.builder().id(updatedFoundSale.getId()).customer(foundCustomer).totalPrice(discountOrAddition).build();
        return ResponseEntity.status(HttpStatus.OK).body(service.update(updatedFoundSale));
    }

    @Override
    @DeleteMapping("/{sale_id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "sale_id") UUID saleId) {
        Sale deletedFoundSale = foundRuleOfThree(saleId, service);
        if (!deletedFoundSale.getItemSales().isEmpty())
            for (Sale.ItemSale deletedItem : deletedFoundSale.getItemSales()) deleteItem(deletedItem.getId());

        service.delete(deletedFoundSale.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sale-cart deleted successfully!");
    }

    @PostMapping("/{sale_id}/items")
    public ResponseEntity<Object> createItem(@PathVariable(value = "sale_id") UUID saleId,
                                             @RequestBody @Valid SaleDTO.ItemSaleDTO itemDto) {
        Sale foundSale = foundRuleOfThree(saleId, service);
        Product foundProduct = foundRuleOfThree(itemDto.getProductId(), productService);
        if (itemDto.getQuantity() <= 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quantity can't be zero.");
        if (foundProduct.getQuantity() < itemDto.getQuantity())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not to Products in stock.");

        foundProduct.setQuantity(foundProduct.getQuantity() - itemDto.getQuantity());
        BigDecimal dtoQuantityForBigDecimal = BigDecimal.valueOf(itemDto.getQuantity());

        Sale.ItemSale createdItem = Sale.ItemSale.builder()
                .sale(foundSale)
                .product(foundProduct)
                .price(foundProduct.getPrice().multiply(dtoQuantityForBigDecimal))
                .quantity(itemDto.getQuantity())
                .build();
        foundSale.setTotalPrice(foundSale.getTotalPrice().add(createdItem.getPrice()));
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.create(createdItem));
    }

    @GetMapping("/items/{item_id}")
    public ResponseEntity<Object> findItemById(@PathVariable(value = "item_id") UUID itemId) {
        Sale.ItemSale foundItemById = foundRuleOfThree(itemId, itemService);
        return ResponseEntity.status(HttpStatus.OK).body(foundItemById);
    }

    @PutMapping("/items/{item_id}")
    public ResponseEntity<Object> updateItem(@PathVariable(value = "item_id") UUID itemId,
                                             @RequestBody @Valid SaleDTO.ItemSaleDTO itemDto) {
        Sale.ItemSale updatedFoundItem = foundRuleOfThree(itemId, itemService);
        Sale foundSale = foundRuleOfThree(updatedFoundItem.getSale().getId(), service);
        Product foundProduct = foundRuleOfThree(itemDto.getProductId(), productService);
        if (itemDto.getQuantity() <= 0 && updatedFoundItem.getQuantity() <= 1)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quantity can't be zero.");
        if (foundProduct.getQuantity() < itemDto.getQuantity())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not to Products in stock.");

        foundProduct.setQuantity(foundProduct.getQuantity() - itemDto.getQuantity());
        BigDecimal dtoQuantityForBigDecimal = BigDecimal.valueOf(itemDto.getQuantity());
        BigDecimal updatedPrice = foundProduct.getPrice().multiply(dtoQuantityForBigDecimal);
        updatedFoundItem = Sale.ItemSale.builder()
                .id(updatedFoundItem.getId())
                .sale(foundSale).product(foundProduct)
                .price(updatedFoundItem.getPrice().add(updatedPrice))
                .quantity(updatedFoundItem.getQuantity() + itemDto.getQuantity())
                .build();
        foundSale.setTotalPrice(foundSale.getTotalPrice().add(updatedPrice));
        return ResponseEntity.status(HttpStatus.OK).body(itemService.update(updatedFoundItem));
    }

    @DeleteMapping("/items/{item_id}")
    public ResponseEntity<Object> deleteItem(@PathVariable(value = "item_id") UUID itemId) {
        Sale.ItemSale deletedFoundItem = foundRuleOfThree(itemId, itemService);
        Sale foundSale = foundRuleOfThree(deletedFoundItem.getSale().getId(), service);
        Product foundProduct = foundRuleOfThree(deletedFoundItem.getProduct().getId(), productService);

        foundProduct.setQuantity(foundProduct.getQuantity() + deletedFoundItem.getQuantity());
        foundSale.setTotalPrice(foundSale.getTotalPrice().subtract(deletedFoundItem.getPrice()));
        itemService.delete(deletedFoundItem.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Item deleted successfully!");
    }
}
