package com.sparta.delivery.category.entity;

import com.sparta.delivery.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "p_category")
@SQLRestriction("deleted_at is null")
public class Category extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "store_category_id")
    private UUID id;

    @Column(name = "store_category_name")
    private String name;

    public Category(String categoryName) {
        name = categoryName;

    }

    public void updateName(String name) {
       this.name = name;
    }

}
