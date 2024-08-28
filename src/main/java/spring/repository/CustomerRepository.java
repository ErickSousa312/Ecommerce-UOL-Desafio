package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.model.Costumer;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Costumer, Long> {
    Optional<Costumer> findByName(String name);
}
