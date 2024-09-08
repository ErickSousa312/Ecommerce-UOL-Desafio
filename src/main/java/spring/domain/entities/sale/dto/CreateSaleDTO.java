package spring.domain.entities.sale.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@Getter @Setter
public class CreateSaleDTO {
    private LocalDateTime date;
    private BigDecimal totalAmount;
    private Map<Long,Integer> productQuantitiesAndIdProducts;


}