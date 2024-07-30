package pe.ahn.mdpicker.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "brand")
public class Brand {
    @Id @Column(name = "brand_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandId;

    @Column(nullable = false, length = 10, name = "brand_name")
    private String brandName;

    @NonNull
    @Column(nullable = false, length = 1, name = "use_yn")
    private String useYn;

    @OneToMany
    @JsonManagedReference
    @JoinColumn(name = "brand_id")
    private List<CategoryPrice> categoryList = new ArrayList<CategoryPrice>();

    public Brand(String brandName, @NonNull String useYn) {
        this.brandName = brandName;
        this.useYn = useYn;
    }
}
