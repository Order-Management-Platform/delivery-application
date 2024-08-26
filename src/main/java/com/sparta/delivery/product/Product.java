package com.sparta.delivery.product;

import com.sparta.delivery.store.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STORE_ID")
    private Store store;

    private String name;
    private int price;
    private String description;
    private Boolean soldOut = false;

}
