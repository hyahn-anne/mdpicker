package pe.ahn.mdpicker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.ahn.mdpicker.model.PriceQueryResultInterface;
import pe.ahn.mdpicker.model.brand.BrandParam;
import pe.ahn.mdpicker.model.category.CategoryListItem;
import pe.ahn.mdpicker.model.category.CategoryInfo;
import pe.ahn.mdpicker.model.entity.Brand;
import pe.ahn.mdpicker.model.entity.CategoryPrice;
import pe.ahn.mdpicker.model.price.PriceModel;
import pe.ahn.mdpicker.model.response.ErrorCode;
import pe.ahn.mdpicker.repo.BrandRepository;
import pe.ahn.mdpicker.repo.PriceRepository;
import pe.ahn.mdpicker.system.ApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class DataService {
    @Autowired
    PriceRepository priceRepository;

    @Autowired
    BrandRepository brandRepository;

    public PriceModel fetchMinPriceBrand() {
        PriceModel result = new PriceModel();
        List<PriceQueryResultInterface> categoryList = priceRepository.findAllMinPriceBrand();

        List<CategoryListItem> responseCategoryList = new ArrayList<>();

        for (PriceQueryResultInterface listItem : categoryList) {
            Brand b = brandRepository.findById(listItem.getBrand_id())
                    .orElseThrow(() -> new ApiException("브랜드가 없습니다.", ErrorCode.BAD_REQUEST));

            responseCategoryList.add(
                    new CategoryListItem(
                            CategoryInfo.getCategoryInfo(listItem.getCategory_Type_id()),
                            listItem.getPrice(),
                            b.getBrandName()
                    )
            );
        }
        Long totalPrice = responseCategoryList.stream().mapToLong(CategoryListItem::getPrice).sum();
        result.setTotalPrice(totalPrice);
        result.setCategories(responseCategoryList);
        return result;
    }

    public PriceModel findBrandOrderByPrice(String order) throws NullPointerException {
        if (!(order.equals(PriceOrder.ASC) || order.equals(PriceOrder.DESC))) {
            throw new ApiException("정렬 기준을 확인하세요. (최저가: asc / 최고가: desc)", ErrorCode.BAD_REQUEST);
        }
        PriceModel result = priceRepository.getBrandOrderByPrice(order);
        List<CategoryListItem> categoryList = priceRepository.getPricesByBrand(result.getBrandId());
        for (CategoryListItem listItem : categoryList) {
            listItem.setCategory(Objects.requireNonNull(
                    CategoryInfo.getCategoryInfo(listItem.getCategoryTypeId()))
            );
        }
        result.setCategories(categoryList);
        return result;
    }

    public PriceModel fetchMinMaxPriceBrandByCategory(Long categoryTypeId) {
        String categoryInfo = CategoryInfo.getCategoryInfo(categoryTypeId);

        if (categoryInfo == null) {
            throw new ApiException("카테고리 정보가 없습니다.", ErrorCode.BAD_REQUEST);
        }

        List<CategoryListItem> minPrice = priceRepository.getMinBrandByCategory(categoryTypeId);
        List<CategoryListItem> maxPrice = priceRepository.getMaxBrandByCategory(categoryTypeId);
        return new PriceModel(
                categoryTypeId,
                categoryInfo,
                minPrice,
                maxPrice
        );
    }

    public BrandParam findBrand(Long brandId) {
        Brand brand = brandRepository
                .findById(brandId)
                .orElseThrow(() -> new ApiException("브랜드가 없습니다.", ErrorCode.BAD_REQUEST));

        List<CategoryPrice> categoryPrices = brand.getCategoryList();
        List<CategoryListItem> categoryListItems = categoryPrices.stream().map(
            categoryPrice -> {
                CategoryListItem listItem = new CategoryListItem();
                listItem.setCategory(categoryPrice.getCategoryTypeName(categoryPrice.getCategoryTypeId()));
                listItem.setPrice(categoryPrice.getPrice());
                listItem.setCategoryTypeId(categoryPrice.getCategoryId());
                listItem.setCategoryTypeId(categoryPrice.getCategoryTypeId());
                return listItem;
            }).toList();

        return new BrandParam(
                brand.getBrandId(),
                categoryListItems,
                brand.getBrandName(),
                brand.getUseYn());
    }

    public Long insertBrandAndPrice(Brand brand) {
        Brand newBrand = new Brand(brand.getBrandName(), brand.getUseYn());
        brandRepository.save(newBrand);

        List<CategoryPrice> categoryList = brand.getCategoryList();
        for (CategoryPrice categoryPrice : categoryList) {
            categoryPrice.setBrand(newBrand);
            priceRepository.save(categoryPrice);
        }
        System.out.println(categoryList.get(0).getCategoryId());
        return newBrand.getBrandId();
    }

    public Long updateBrandAndPrice(Brand requestBrand) {
        Brand brand = brandRepository
                .findById(requestBrand.getBrandId())
                .orElseThrow(() -> new ApiException("브랜드가 없습니다.", ErrorCode.BAD_REQUEST));

        brand.setBrandName(requestBrand.getBrandName());
        brandRepository.save(brand);

        List<CategoryPrice> categoryList = requestBrand.getCategoryList();
        for (CategoryPrice categoryPrice : categoryList) {
            CategoryPrice selectedCategoryPrice = priceRepository
                    .findById(categoryPrice.getCategoryId())
                    .orElseThrow(() -> new ApiException("카테고리 정보가 없습니다.", ErrorCode.BAD_REQUEST));
            selectedCategoryPrice.setBrand(brand);
            selectedCategoryPrice.setPrice(categoryPrice.getPrice());
            priceRepository.save(selectedCategoryPrice);
        }
        return brand.getBrandId();
    }

    public Long deleteBrandData(Long brandId) {
        Brand retrievedBrand = brandRepository
                .findById(brandId)
                .orElseThrow(() -> new ApiException("브랜드가 없습니다.", ErrorCode.BAD_REQUEST));

        List<CategoryPrice> categoryList = retrievedBrand.getCategoryList();
        priceRepository.deleteAll(categoryList);
        brandRepository.delete(retrievedBrand);
        return retrievedBrand.getBrandId();
    }
}
