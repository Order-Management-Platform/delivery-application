package com.sparta.delivery.product.entity;

import com.sparta.delivery.common.BaseEntity;
import com.sparta.delivery.product.dto.ProductModifyRequestDto;
import com.sparta.delivery.store.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name="p_product")
@SQLRestriction("deleted_at IS NULL")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "store_id")
    private Store store;

    private String name;
    private int price;
    private String description;
    @Builder.Default
    private Boolean soldOut = false;

    public void  modify(ProductModifyRequestDto dto){
        this.name = dto.getName();
        this.price = dto.getPrice();
        this.description = dto.getDescription();
    }


}
