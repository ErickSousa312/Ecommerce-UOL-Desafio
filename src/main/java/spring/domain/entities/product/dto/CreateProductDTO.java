package spring.domain.entities.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateProductDTO {
    private String productName;
    private Double price;
    private String status;
    private Integer stock;
}