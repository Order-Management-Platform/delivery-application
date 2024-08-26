package com.sparta.delivery.store.entity;

import com.sparta.delivery.common.BaseEntity;
import com.sparta.delivery.product.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "p_store")
@Getter
public class Store extends BaseEntity {

    //todo : 정수형, uuid사용
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

   /* @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID")
    private User user;

    @ManyToOne(fetch=FetchType.Lazy)
    @JoinColumn(name="CATEGORY_ID")
    private category category;

    @ManyToOne(fetch=FetchType.LAZY)
    private region region;
    */

    @OneToMany(mappedBy="store",
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<Product> productList = new ArrayList<Product>();

    private String name;
    private String address;
    private String tel;
    private Integer minPrice;
    private String description;
    //todo : 운영시간 -open close
    @DateTimeFormat
    private String operatingTime;
}
