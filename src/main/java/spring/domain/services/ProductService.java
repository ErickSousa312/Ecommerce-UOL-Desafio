package spring.domain.services;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import spring.domain.entities.product.dto.CreateProductDTO;
import spring.domain.entities.product.mapper.ProductMapper;
import spring.domain.entities.product.model.Product;
import spring.domain.repositories.ProductRepository;
import spring.web.execption.EntityAlreadyExistsException;
import spring.web.execption.MustNotBeNullException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Product saveProduct(CreateProductDTO productDTO) throws EntityAlreadyExistsException {
        if(productDTO==null){
            throw new MustNotBeNullException("Product", HttpStatus.BAD_REQUEST);
        }
        Optional<Product> product =  productRepository.findByProductName(productDTO.getProductName());
        if(product.isPresent()){
            throw new EntityAlreadyExistsException("Product", HttpStatus.CONFLICT);
        }
        Product productConverted = productMapper.toProduct(productDTO);
        return productRepository.save(productConverted);
    }
}
