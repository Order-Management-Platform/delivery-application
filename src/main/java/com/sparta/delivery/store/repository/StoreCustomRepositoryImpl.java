package com.sparta.delivery.store.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.delivery.store.entity.Store;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.sparta.delivery.store.entity.QStore.store;

@Repository
@AllArgsConstructor
public class StoreCustomRepositoryImpl implements StoreCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Store> findAllByCondition(UUID regionId, UUID categoryId, String keyWord, Pageable pageable) {
        JPQLQuery<Store> query = jpaQueryFactory
                .selectFrom(store)
                .where(store.region.id.eq(regionId));
        if (keyWord != null) {
            query.where(store.name.contains(keyWord));
        }

        if (categoryId != null) {
            query.where(store.category.id.eq(categoryId));
        }

        List<Store> storeList = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(SortDirection(pageable))
                .fetch();

        JPQLQuery<Store> countQuery = jpaQueryFactory
                .selectFrom(store)
                .where(store.region.id.eq(regionId));

        if (keyWord != null) {
            query.where(store.name.contains(keyWord));
        }

        if (categoryId != null) {
            countQuery.where(store.category.id.eq(categoryId));
        }

        return PageableExecutionUtils.getPage(storeList, pageable, countQuery::fetchCount);
    }

    private OrderSpecifier<?> SortDirection(Pageable pageable) {
        if (pageable.getSort().isSorted()) {
            return pageable.getSort().stream()
                    .map(order -> {
                        String property = order.getProperty();
                        boolean isAscending = order.isAscending();

                        if ("name".equals(property)) {
                            return isAscending ? store.name.asc() : store.name.desc();
                        } else {
                            return isAscending ? store.rating.asc() : store.rating.desc();
                        }
                    })
                    .findFirst()
                    .orElse(store.rating.desc());
        }
        return store.rating.desc();
    }
}
