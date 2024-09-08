package spring.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.domain.entities.sale.model.Sale;

import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
}
