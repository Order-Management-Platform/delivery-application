package com.sparta.delivery.review;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.sparta.delivery.review.QReview.review;

@Repository
@AllArgsConstructor
public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Page<Review> findAllByStoreWithOwner(UUID storeId, String keyWord, String type, Pageable pageable) {
        List<Review> reviewList= jpaQueryFactory
                .selectFrom(review)
                .where(
                        review.store.id.eq(storeId),
                        containsKey(keyWord,type)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(SortDirection(pageable))
                .fetch();

        JPQLQuery<Review> count = jpaQueryFactory
                .selectFrom(review)
                .where(
                        review.store.id.eq(storeId),
                        containsKey(keyWord, type)
                );

        return PageableExecutionUtils.getPage(reviewList, pageable, count::fetchCount);


    }

    private BooleanExpression containsKey(String keyWord, String type) {
        return type.equals("name") ? review.user.nickName.contains(keyWord)
                : review.content.contains(keyWord);
    }

    private OrderSpecifier<?> SortDirection(Pageable pageable) {
        if(pageable.getSort().isSorted()){
            return pageable.getSort().stream()
                    .map(order -> order.isAscending() ? review.createdAt.asc() :
                            review.createdAt.desc())
                    .findFirst()
                    .orElse(review.createdAt.desc());
        }
        return review.createdAt.desc();
    }
}
