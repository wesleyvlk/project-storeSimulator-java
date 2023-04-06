package com.github.wesleyvlk.storesimulator.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime dateTime;

    @OneToMany(mappedBy = "sale")
    private List<ItemSale> itemSales;

}
