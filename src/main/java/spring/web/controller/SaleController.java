package spring.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.domain.entities.sale.dto.CreateSaleDTO;

@RestController
@RequestMapping("/sale")
public class SaleController {
    @PostMapping
    public ResponseEntity<String> save(@RequestBody CreateSaleDTO sale){
        return ResponseEntity.status(HttpStatus.OK).body("deu Berto ");
    }
}
