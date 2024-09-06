package spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.domain.entities.product.dto.CreateProductDTO;
import spring.domain.entities.product.dto.ResponseProductDTO;
import spring.domain.entities.product.mapper.ProductMapper;
import spring.domain.entities.product.model.Product;
import spring.domain.repositories.ProductRepository;
import spring.domain.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody CreateProductDTO createProductDTO) {
        Product createdProduct = productService.save(createProductDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> listProduct = productService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(listProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        Boolean DeleteEntity = productService.deleteById(id);
        if (DeleteEntity) {
            return ResponseEntity.status(HttpStatus.OK).body("Successful product delete");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PatchMapping({"/{id}"})
    public ResponseEntity<ResponseProductDTO> updateProduct(@RequestBody CreateProductDTO productDTO, @PathVariable Long id) {
        Product productUpdated = productService.updateById(productDTO, id);
        ResponseProductDTO responseProductDTO = productMapper.toResponseProductDTO(productUpdated);
        return ResponseEntity.status(HttpStatus.OK).body(responseProductDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

}
