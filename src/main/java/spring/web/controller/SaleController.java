package spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.domain.entities.sale.dto.CreateSaleDTO;
import spring.domain.entities.sale.model.Sale;
import spring.domain.repositories.SaleRepository;
import spring.domain.services.SaleService;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    public ResponseEntity<Sale> save(@RequestBody CreateSaleDTO saleDTO){
        Sale sale = saleService.save(saleDTO);
        return ResponseEntity.status(HttpStatus.OK).body(sale);
    }
}
