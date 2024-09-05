package spring.domain.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseProductDTO {
    private String id;
    private String productName;
    private Double price;
    private String status;
    private Number stock;
}
