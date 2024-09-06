package spring.domain.entities.sale.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter @Setter
public class CreateSaleDTO {
    private Long id;
    private LocalDateTime date;
    private BigDecimal totalAmount;
    private Set<Long> productIds;
}