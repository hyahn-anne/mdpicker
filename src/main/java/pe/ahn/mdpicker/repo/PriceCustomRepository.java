package pe.ahn.mdpicker.repo;

import pe.ahn.mdpicker.model.category.CategoryListItem;
import pe.ahn.mdpicker.model.price.PriceModel;

import java.util.List;

public interface PriceCustomRepository {
    PriceModel getBrandOrderByPrice(String order);
    List<CategoryListItem> getPricesByBrand(Long brandId);
    List<CategoryListItem> getMinBrandByCategory(Long categoryId);
    List<CategoryListItem> getMaxBrandByCategory(Long categoryId);
}
