package pe.ahn.mdpicker.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.ahn.mdpicker.model.PriceQueryResultInterface;
import pe.ahn.mdpicker.model.entity.CategoryPrice;

import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<CategoryPrice, Long>, PriceCustomRepository {
    @Query(value = "SELECT * " +
                    "FROM (" +
                        "SELECT " +
                            "c.brand_id, c.category_type_id, c.price, RANK() OVER (PARTITION BY c.category_type_id ORDER BY s.total ASC) AS brand_rank " +
                        "FROM category_price c " +
                        "INNER JOIN (" +
                            "SELECT brand_id, SUM(price) as total " +
                            "FROM category_price " +
                            "GROUP BY brand_id" +
                        ") s on " +
                            "c.brand_id = s.brand_id " +
                        "WHERE (category_type_id, price) in (" +
                            "SELECT category_type_id, min(price) as price " +
                            "FROM category_price " +
                            "GROUP BY category_type_id" +
                        ") " +
                    ") as r " +
                    "WHERE r.brand_rank <= 1;"
    , nativeQuery = true)
    List<PriceQueryResultInterface> findAllMinPriceBrand();
}