package spring.domain.entities.stock;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import spring.domain.entities.product.model.Product;

@Entity
@Table(name = "stock")
@Setter @Getter
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToOne
    private Product product;
    private int quantity;
}
