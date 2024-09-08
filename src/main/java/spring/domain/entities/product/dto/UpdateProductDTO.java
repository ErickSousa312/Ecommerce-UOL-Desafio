package spring.domain.entities.product.dto;

import lombok.Getter;
import lombok.Setter;
import spring.domain.entities.user.model.Authority;

import java.util.Set;

@Getter @Setter
public class UpdateProductDTO {
    private Long id;
    private String productName;
    private Double price;
    private String status;
    private Integer stock;
    private Set<Authority> authorities;
}
