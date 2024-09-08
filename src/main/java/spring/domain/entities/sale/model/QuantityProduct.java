package spring.domain.entities.sale.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import spring.domain.entities.product.model.Product;

@Entity
@Table(name = "quantity_product")
@Setter @Getter
public class QuantityProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "sale_id")
    private Sale sale;
}
