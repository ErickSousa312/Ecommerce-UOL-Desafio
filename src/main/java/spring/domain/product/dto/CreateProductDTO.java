package spring.domain.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateProductDTO {
    private String id;
    private String productName;
    private Double price;
    private String status;
    private Integer stock;
}