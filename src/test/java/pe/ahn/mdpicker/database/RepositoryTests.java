package pe.ahn.mdpicker.database;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pe.ahn.mdpicker.model.category.CategoryListItem;
import pe.ahn.mdpicker.model.entity.Brand;
import pe.ahn.mdpicker.model.entity.CategoryPrice;
import pe.ahn.mdpicker.repo.BrandRepository;
import pe.ahn.mdpicker.repo.PriceRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RepositoryTests {
    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Test
    public void getMaxPriceBrandByCategory() {
        Long categoryTypeId = 1L;
        List<CategoryListItem> priceList = priceRepository.getMaxBrandByCategory(categoryTypeId);
        assertEquals(priceList.get(0).getPrice(), 11400);
        assertEquals(priceList.get(0).getBrand(), "I");
    }

    @Test
    public void getMinPriceBrandByCategory() {
        Long categoryTypeId = 1L;
        List<CategoryListItem> priceList = priceRepository.getMinBrandByCategory(categoryTypeId);
        assertEquals(priceList.get(0).getPrice(), 10000);
        assertEquals(priceList.get(0).getBrand(), "C");
    }

    @Test
    @Transactional
    public void addNewBrandAndProducts() {
        String brandName = "TEST_BRAND";
        String useYn = "Y";

        Brand brand = new Brand(brandName, useYn);

        List<CategoryPrice> categories = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            categories.add(
                    new CategoryPrice(100000L, brand, (long) i)
            );
        }
        brand.setCategoryList(categories);
        brandRepository.save(brand);
        priceRepository.saveAll(categories);

        Brand retreivedBrand = brandRepository.findById(brand.getBrandId()).orElse(null);
        assert(retreivedBrand != null);
        assert(retreivedBrand.getCategoryList() != null);
        assertEquals(retreivedBrand.getBrandName(), brandName);
        assertEquals(retreivedBrand.getUseYn(), useYn);

        List<CategoryPrice> retrievedCategories = retreivedBrand.getCategoryList();
        assertEquals(retrievedCategories.size(), categories.size());
        assertEquals(retrievedCategories.get(0).getCategoryTypeId(), categories.get(0).getCategoryTypeId());
        assertEquals(retrievedCategories.get(0).getBrand().getBrandName(), categories.get(0).getBrand().getBrandName());
    }
}
