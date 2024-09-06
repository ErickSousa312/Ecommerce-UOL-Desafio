package spring.domain.entities.sale.model;
import jakarta.persistence.*;
import spring.domain.entities.product.model.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "sale")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    private BigDecimal totalAmount;

    @ManyToMany
    @JoinTable(
            name = "sale_product",
            joinColumns = @JoinColumn(name = "sale_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products;

    public void validateSale() {
        if (this.products == null || this.products.isEmpty()) {
            throw new IllegalArgumentException("Sale must have at least one product");
        }
    }

    public void calculateTotalAmount() {
        this.totalAmount = products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
