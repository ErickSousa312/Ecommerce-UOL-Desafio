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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="product_name")
    private String productName;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private ProductStatus status; // ACTIVE, INACTIVE

    private Integer stock;

    public void validatePrice() {
        if (this.price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
    }

    public void addStock(Integer quantity) {
        if (this.stock == null || this.stock < quantity) {
            throw new IllegalArgumentException("QuantityProduct must be greater than or equal to stock.");
        }
        this.stock += quantity;
    }

    public void reducerStock(Integer quantity) {
        if (this.stock == null || this.stock < quantity) {
            throw new IllegalArgumentException("QuantityProduct must be greater than or equal to stock.");
        }
        this.stock -= quantity;
    }


}
