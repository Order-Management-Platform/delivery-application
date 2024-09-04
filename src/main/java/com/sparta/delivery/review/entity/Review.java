package com.sparta.delivery.review.entity;

import com.sparta.delivery.common.BaseEntity;
import com.sparta.delivery.review.dto.ReviewModifyRequestDto;
import com.sparta.delivery.store.entity.Store;
import com.sparta.delivery.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "p_review")
@Getter
@Builder
@SQLRestriction("deleted_at IS NULL")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private User user;

    private String content;

    private int rating;

    private boolean declaration;

    private String declarationMessage;

    public void modify(ReviewModifyRequestDto dto) {
        this.content = dto.getContent();
        this.rating = dto.getRating();
    }

    public void declaration(String content) {
        this.content = content;
        this.declaration = true;
    }
}
