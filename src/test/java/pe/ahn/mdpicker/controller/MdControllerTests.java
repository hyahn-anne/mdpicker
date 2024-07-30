package pe.ahn.mdpicker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pe.ahn.mdpicker.model.entity.Brand;
import pe.ahn.mdpicker.model.entity.CategoryPrice;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MdControllerTests {
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();

    Brand newBrand;

    @BeforeEach
    public void setUp() {
        newBrand = new Brand("TestBrand", "Y");
        long price = 50000L;
        List<CategoryPrice> categories = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            categories.add(new CategoryPrice(price, newBrand, (long) i));
            price += 10000;
        }
        newBrand.setCategoryList(categories);
    }

    @Test
    @Order(5)
    public void priceSummary() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/md/price/summary"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(6)
    public void getMinPriceBrand() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/md/price/brand").queryParam("order", "asc"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(7)
    public void getMaxPriceBrand() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/md/price/brand").queryParam("order", "desc"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(8)
    public void getPriceByCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/md/price/category").queryParam("id", "3"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(9)
    public void notFoundPriceByCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/md/price/category").queryParam("id", "11"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(1)
    public void addBrand() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/md/brand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(newBrand)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(2)
    public void findBrandById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/md/brand").queryParam("id", "10"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(3)
    public void editBrand() throws Exception {
        Brand existingBrand = newBrand;
        existingBrand.setBrandId(10L);

        CategoryPrice price = newBrand.getCategoryList().get(0);
        price.setCategoryId(73L);
        price.setPrice(100000L);

        List<CategoryPrice> newCategories = new ArrayList<>();
        newCategories.add(price);
        existingBrand.setCategoryList(newCategories);

        mockMvc.perform(MockMvcRequestBuilders.put("/md/brand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(existingBrand)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(4)
    public void deleteBrand() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/md/brand").queryParam("id", "10"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
