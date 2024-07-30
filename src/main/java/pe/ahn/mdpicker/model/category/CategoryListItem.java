package pe.ahn.mdpicker.model.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryListItem {
    private Long price;
    private String category;
    private String brand;
    private Long categoryTypeId;
    private Long totalPrice;

    public CategoryListItem(Long categoryTypeId, Long price) {
        this.categoryTypeId = categoryTypeId;
        this.price = price;
    }

    public CategoryListItem(String category, Long price, String brand) {
        this.category = category;
        this.price = price;
        this.brand = brand;
    }

    public CategoryListItem(Long price, Long categoryTypeId, String brand, Long totalPrice) {
        this.brand = brand;
        this.price = price;
        this.categoryTypeId = categoryTypeId;
        this.totalPrice = totalPrice;
    }

    public CategoryListItem(String brand, Long price) {
        this.brand = brand;
        this.price = price;
    }
}
