package spring.domain.entities.sale.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import spring.domain.entities.user.model.Customer;
import spring.web.exceptions.InvalidSaleException;

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

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Customer costumer;

    private BigDecimal totalAmount = BigDecimal.valueOf(0);

    @OneToMany(mappedBy = "sale", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<QuantityProduct> products;

    public void validateSale() {
        if (this.products == null || this.products.isEmpty()) {
            throw new InvalidSaleException("Sale must have at least one product", HttpStatus.BAD_REQUEST);
        }
    }

    public void addAmount(BigDecimal quantity) {
        if (quantity == null) {
            throw new IllegalArgumentException("Quantity cannot be null");
        }
        if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (this.totalAmount == null) {
            throw new IllegalStateException("Total amount cannot be null");
        }
        this.totalAmount = this.totalAmount.add(quantity);
    }

}