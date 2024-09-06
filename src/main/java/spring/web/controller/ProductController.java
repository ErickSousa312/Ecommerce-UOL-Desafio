package spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.domain.entities.product.dto.CreateProductDTO;
import spring.domain.entities.product.mapper.ProductMapper;
import spring.domain.entities.product.model.Product;
import spring.domain.services.ProductService;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductMapper productMapper;
    private final ProductService productService;

    @PostMapping("/product")
    public ResponseEntity<Product> addProduct(@RequestBody CreateProductDTO createProductDTO) {
            Product createdProduct = productService.saveProduct(createProductDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }
}
