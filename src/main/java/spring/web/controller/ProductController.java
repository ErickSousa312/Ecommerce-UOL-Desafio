package spring.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import spring.domain.entities.product.dto.CreateProductDTO;
import spring.domain.entities.product.dto.ResponseProductDTO;
import spring.domain.entities.product.dto.UpdateProductDTO;
import spring.domain.entities.product.mapper.ProductMapper;
import spring.domain.entities.product.model.Product;
import spring.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Product> addProduct(@Valid @RequestBody CreateProductDTO createProductDTO) {
        Product createdProduct = productService.save(createProductDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> listProduct = productService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(listProduct);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id, Authentication auth) {
        Boolean DeleteEntity = productService.deleteById(id);
        if (DeleteEntity) {
            return ResponseEntity.status(HttpStatus.OK).body("Successful product delete");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseProductDTO> updatePutProduct(@Valid @RequestBody CreateProductDTO productDTO, @PathVariable Long id) {
        Product productUpdated = productService.updateById(productDTO, id);
        ResponseProductDTO responseProductDTO = productMapper.toResponseProductDTO(productUpdated);
        return ResponseEntity.status(HttpStatus.OK).body(responseProductDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseProductDTO> updatePatchProduct(@Valid @RequestBody UpdateProductDTO productDTO, @PathVariable Long id) {
        Product productUpdated = productService.updatePartialById(productDTO, id);
        ResponseProductDTO responseProductDTO = productMapper.toResponseProductDTO(productUpdated);
        return ResponseEntity.status(HttpStatus.OK).body(responseProductDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

}
