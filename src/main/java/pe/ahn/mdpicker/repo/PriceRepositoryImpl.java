package pe.ahn.mdpicker.repo;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pe.ahn.mdpicker.model.category.CategoryListItem;
import pe.ahn.mdpicker.model.price.PriceModel;
import pe.ahn.mdpicker.service.PriceOrder;

import java.util.List;

import static pe.ahn.mdpicker.model.entity.QCategoryPrice.categoryPrice;


@Repository
@AllArgsConstructor
public class PriceRepositoryImpl implements PriceCustomRepository {
    private final String USE_Y = "Y";
    private final JPAQueryFactory queryFactory;

    @Override
    public PriceModel getBrandOrderByPrice(String order) {
        JPAQuery<PriceModel> query = null;

        query = queryFactory
                .select(Projections.constructor(
                        PriceModel.class,
                        categoryPrice.brand.brandId,
                        categoryPrice.brand.brandName,
                        categoryPrice.price.sum()
                ))
                .from(categoryPrice)
                .where(categoryPrice.brand.useYn.eq(this.USE_Y))
                .groupBy(categoryPrice.brand.brandId);

        if (order.equals(PriceOrder.ASC)) {
            query = query.orderBy(categoryPrice.price.sum().asc());
        } else {
            query = query.orderBy(categoryPrice.price.sum().desc());
        }
        return query.fetchFirst();
    }

    @Override
    public List<CategoryListItem> getPricesByBrand(Long brandId) {
        return queryFactory
                .select(Projections.constructor(CategoryListItem.class, categoryPrice.categoryTypeId, categoryPrice.price))
                .from(categoryPrice)
                .where(categoryPrice.brand.brandId.eq(brandId)
                        .and(categoryPrice.brand.useYn.eq(this.USE_Y)))
                .fetch();
    }

    @Override
    public List<CategoryListItem> getMinBrandByCategory(Long categoryId) {
        return getBrandByCategory(categoryId, PriceOrder.ASC);
    }

    @Override
    public List<CategoryListItem> getMaxBrandByCategory(Long categoryId) {
        return getBrandByCategory(categoryId, PriceOrder.DESC);
    }

    private List<CategoryListItem> getBrandByCategory(Long categoryId, String order) {
        JPAQuery<CategoryListItem> query = null;

        if (order.equals(PriceOrder.ASC)) {
            query = queryFactory
                    .select(Projections.constructor(CategoryListItem.class, categoryPrice.brand.brandName, categoryPrice.price.min()));
        } else {
            query = queryFactory
                    .select(Projections.constructor(CategoryListItem.class, categoryPrice.brand.brandName, categoryPrice.price.max()));
        }

        query = query
                .from(categoryPrice)
                .where(categoryPrice.categoryTypeId.eq(categoryId)
                        .and(categoryPrice.brand.useYn.eq(this.USE_Y)))
                .groupBy(categoryPrice.brand.brandId);

        if (order.equals(PriceOrder.ASC)) {
            query = query.orderBy(categoryPrice.price.min().asc());
        } else {
            query = query.orderBy(categoryPrice.price.max().desc());
        }
        return query
                .limit(1L)
                .fetch();
    }
}