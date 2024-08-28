package com.sparta.delivery.store.entity;

import com.sparta.delivery.category.entity.Category;
import com.sparta.delivery.common.BaseEntity;
import com.sparta.delivery.product.entity.Product;
import com.sparta.delivery.region.entity.Region;
import com.sparta.delivery.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "p_store")
@Getter
@Builder
public class Store extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne(fetch=FetchType.LAZY)
    private Region region;

    @OneToMany(mappedBy="store",
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<Product> productList = new ArrayList<Product>();

    private String name;
    private String address;
    private String tel;
    private Integer minPrice;
    private String description;
    private String operatingTime;
}
