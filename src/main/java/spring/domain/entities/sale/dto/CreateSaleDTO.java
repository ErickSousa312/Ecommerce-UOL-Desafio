package spring.domain.entities.sale.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@Getter @Setter
public class CreateSaleDTO {
    @NotNull(message = "Date cannot be null")
    private LocalDateTime date;

    private Long clientId;

    @DecimalMin(value = "0.00", inclusive = true, message = "Total amount must be positive")
    private BigDecimal totalAmount;

    @NotNull(message = "Product quantities and IDs cannot be null")
    @Size(min = 1, message = "At least one product must be specified")
    private Map<@NotNull Long, @Min(value = 1, message = "Quantity must be at least 1") Integer> productQuantitiesAndIdProducts;
}