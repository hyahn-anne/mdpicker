package pe.ahn.mdpicker.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.ahn.mdpicker.model.category.CategoryInfo;

@Entity
@Table(name = "category_price")
@Getter
@Setter
@NoArgsConstructor
public class CategoryPrice {
    @Id @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(nullable = false, name = "price")
    private Long price;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column(nullable = false, name = "category_type_id")
    private Long categoryTypeId;

    public CategoryPrice(Long price, Brand brand, Long categoryTypeId) {
        this.price = price;
        this.brand = brand;
        this.categoryTypeId = categoryTypeId;
    }

    public String getCategoryTypeName(Long categoryTypeId) {
        return CategoryInfo.getCategoryInfo(categoryTypeId);
    }
}
