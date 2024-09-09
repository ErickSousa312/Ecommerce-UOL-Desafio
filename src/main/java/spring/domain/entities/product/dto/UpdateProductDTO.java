package spring.domain.entities.product.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import spring.domain.entities.user.model.Authority;

import java.util.Set;

@Getter @Setter
public class UpdateProductDTO {
    private Long id;
    @NotBlank(message = "Product name cannot be blank")
    private String productName;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private Double price;

    @NotBlank(message = "Status cannot be blank")
    private String status;

    @NotNull(message = "Stock cannot be null")
    @Min(value = 0, message = "Stock must be at least 0")
    private Integer stock;
}
