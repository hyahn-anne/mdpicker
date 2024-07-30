package pe.ahn.mdpicker.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.ahn.mdpicker.model.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
}
