package spring.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.domain.entities.sale.model.QuantityProduct;

public interface QuantityProductRepository extends JpaRepository<QuantityProduct, Integer> {
}
