package spring.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.domain.entities.sale.dto.CreateSaleDTO;
import spring.domain.entities.sale.model.Sale;
import spring.domain.services.SaleService;
import spring.web.exceptions.EntityNotFound;

import java.util.List;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    public ResponseEntity<Sale> addSale(@Valid @RequestBody CreateSaleDTO saleDTO){
        Sale sale = saleService.save(saleDTO);
        return ResponseEntity.status(HttpStatus.OK).body(sale);
    }

    @GetMapping
    public ResponseEntity<List<Sale>> getAllSales(){
        List<Sale> sales = saleService.findAll();
        if(sales.isEmpty()){
            throw new EntityNotFound("Sales", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK).body(sales);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sale> findSaleById(@PathVariable Long id){
        Sale sale = saleService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(sale);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sale> updatePutSale(@PathVariable Long id,@Valid @RequestBody CreateSaleDTO saleDTO){
        Sale sale = saleService.updateById(id,saleDTO);
        return ResponseEntity.status(HttpStatus.OK).body(sale);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSaleById(@PathVariable Long id){
        Boolean deleteSale = saleService.deleteById(id);
        if(deleteSale){
            return ResponseEntity.status(HttpStatus.OK).body("Deleted Sale Successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
