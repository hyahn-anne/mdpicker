package pe.ahn.mdpicker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.ahn.mdpicker.model.brand.BrandParam;
import pe.ahn.mdpicker.model.entity.Brand;
import pe.ahn.mdpicker.model.price.PriceModel;
import pe.ahn.mdpicker.model.response.DataResponse;
import pe.ahn.mdpicker.repo.BrandRepository;
import pe.ahn.mdpicker.repo.PriceRepository;
import pe.ahn.mdpicker.service.DataService;

@RestController
@RequestMapping("/md")
public class MdController {
    @Autowired
    DataService dataService;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private PriceRepository priceRepository;

    @GetMapping(path="/price/summary")
    public DataResponse<PriceModel> getCostSummary() {
        return new DataResponse<>(dataService.fetchMinPriceBrand());
    }

    @GetMapping(path="/price/brand")
    public DataResponse<PriceModel> fetchPriceInfo(@RequestParam("order") String order) {
        return new DataResponse<>(
                dataService.findBrandOrderByPrice(order)
        );
    }

    // http://localhost:8000/md/cost?category=1
    @GetMapping(path="/price/category")
    public DataResponse<PriceModel> fetchPricesByCategory(@RequestParam("id") Long categoryTypeId) {
        return new DataResponse<>(
                dataService.fetchMinMaxPriceBrandByCategory(categoryTypeId)
        );
    }

    @GetMapping(path="/brand")
    public DataResponse<BrandParam> fetchBrand(@RequestParam("id") Long brandId) {
        return new DataResponse<>(dataService.findBrand(brandId));
    }

    @PostMapping(path="/brand")
    public DataResponse<Long> addBrand(@RequestBody Brand param) {
        Long brandId = dataService.insertBrandAndPrice(param);
        return new DataResponse<>(brandId);
    }

    @PutMapping(path="/brand")
    public DataResponse<Long> updateBrand(@RequestBody Brand param) {
        Long brandId = dataService.updateBrandAndPrice(param);
        return new DataResponse<>(brandId);
    }

    @DeleteMapping(path="/brand")
    public DataResponse<Long> deleteBrand(@RequestParam("id") Long brandId) {
        Long deletedId = dataService.deleteBrandData(brandId);
        return new DataResponse<>(deletedId);
    }

}
