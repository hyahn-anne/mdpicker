package pe.ahn.mdpicker.model.brand;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pe.ahn.mdpicker.model.category.CategoryListItem;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class BrandParam {
    private Long brandId;
    private List<CategoryListItem> categoryList;
    private String brandName;
    private String useYn;
}
