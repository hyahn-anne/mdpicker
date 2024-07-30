package pe.ahn.mdpicker.model.price;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.ahn.mdpicker.model.category.CategoryListItem;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PriceModel {
    private Long totalPrice;
    private Long brandId;
    private Long categoryId;
    private String brand;
    private String category;
    private List<CategoryListItem> categories;
    private List<CategoryListItem> minPrice;
    private List<CategoryListItem> maxPrice;

    public PriceModel(Long totalPrice, List<CategoryListItem> categories) {
        this.totalPrice = totalPrice;
        this.categories = categories;
    }

    public PriceModel(Long brandId, String brand, Long totalPrice) {
        this.brand = brand;
        this.brandId = brandId;
        this.totalPrice = totalPrice;
    }

    public PriceModel(Long categoryId, String category, List<CategoryListItem> minPrice, List<CategoryListItem> maxPrice) {
        this.category = category;
        this.categoryId = categoryId;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }
}
