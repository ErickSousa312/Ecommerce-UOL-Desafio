package spring.domain.entities.sale.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "sale")
@Getter
@Setter
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    private BigDecimal totalAmount;

    @OneToMany(mappedBy = "sale", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<QuantityProduct> products;

    public void validateSale() {
        if (this.products == null || this.products.isEmpty()) {
            throw new IllegalArgumentException("Sale must have at least one product");
        }
    }

    public void addAmount(BigDecimal quantity) {
        this.totalAmount = this.totalAmount.add(quantity);
    }

}