package spring.domain.entities.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import spring.domain.entities.product.enums.ProductStatus;
import spring.domain.entities.sale.model.Sale;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "product")
@Setter @Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="product_name")
    private String productName;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private ProductStatus status; // ACTIVE, INACTIVE

    private Integer stock;

    @JsonIgnore
    @ManyToMany(mappedBy = "products")
    private Set<Sale> sales;

    public void validatePrice() {
        if (this.price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
    }

    public void checkStock(Integer quantity) {
        if (this.stock == null || this.stock < quantity) {
            throw new IllegalArgumentException("Stock must be greater than or equal to stock.");
        }
    }

    public void deactivate() {
        if (this.sales != null && !this.sales.isEmpty()) {
            throw new IllegalStateException("Product has already been deactivated.");
        }
        this.status = ProductStatus.INACTIVE;
    }
}
