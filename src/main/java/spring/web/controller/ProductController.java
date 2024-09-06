package spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;
import spring.domain.product.dto.CreateProductDTO;
import spring.domain.product.dto.ResponseProductDTO;
import spring.domain.product.mapper.ProductMapper;
import spring.domain.product.model.Product;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductMapper productMapper;

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestBody CreateProductDTO createProductDTO) {
        try {
            System.out.println(createProductDTO);
            Product product = productMapper.toProduct(createProductDTO);
            CreateProductDTO createProductDTO1 = productMapper.toCreateProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
