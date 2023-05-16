package com.github.wesleyvlk.storesimulator.sale.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.wesleyvlk.storesimulator.customer.domain.model.Customer;
import com.github.wesleyvlk.storesimulator.product.domain.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.NumberFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.FetchType.EAGER;

@Entity
@Table(name = "sale", uniqueConstraints = {@UniqueConstraint(columnNames = {"customer_fk"})})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sale implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne(fetch = EAGER)
    @JoinColumn(name = "customer_fk", unique = true, nullable = false)
    private Customer customer;

    @Column
    @NumberFormat(pattern = "%.2f")
    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "sale", fetch = EAGER)
    private List<ItemSale> itemSales;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updatedAt;

    @Entity(name = "item_sale")
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @JsonIgnoreProperties("sale")
    public static class ItemSale implements Serializable {
        private static final Long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private UUID id;

        @ManyToOne(fetch = EAGER)
        @JoinColumn(name = "product_fk")
        private Product product;

        @Column(nullable = false)
        private Long quantity;

        @Column
        @NumberFormat(pattern = "%.2f")
        private BigDecimal price;

        @ManyToOne(fetch = EAGER)
        @JoinColumn(name = "sale_fk")
        private Sale sale;

        @CreationTimestamp
        @Column(nullable = false, updatable = false)
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        private LocalDateTime createdAt;

        @UpdateTimestamp
        @Column(nullable = false)
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        private LocalDateTime updatedAt;
    }

}
