package spring.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import spring.domain.entities.product.dto.CreateProductDTO;
import spring.domain.entities.product.dto.UpdateProductDTO;
import spring.domain.entities.product.mapper.ProductMapper;
import spring.domain.entities.product.model.Product;
import spring.repositories.ProductRepository;
import spring.web.exceptions.EntityAlreadyExistsException;
import spring.web.exceptions.EntityNotFound;
import spring.web.exceptions.MustNotBeNullException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Product save(CreateProductDTO productDTO) {
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

    public List<Product> getAll() {
        List<Product> products = productRepository.findAll();
        if(products.isEmpty()){
            throw new EntityNotFound("Products", HttpStatus.NOT_FOUND);
        }
        return productRepository.findAll();
    }

    public Boolean deleteById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.delete(product.get());
            return true;
        }
        return false;
    }

    public Product updateById(CreateProductDTO productDTO, Long id)  {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new EntityNotFound("Product", HttpStatus.NOT_FOUND);
        }
        Product productConverted = productMapper.toProduct(productDTO);
        productConverted.setId(id);
        return productRepository.save(productConverted);
    }

    public Product updatePartialById(UpdateProductDTO updateProductDTO, Long id)  {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new EntityNotFound("Product", HttpStatus.NOT_FOUND);
        }
        productMapper.updateProductFromDto(updateProductDTO,product.get());
        return productRepository.save(product.get());
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(()->
                new EntityNotFound("Product", HttpStatus.NOT_FOUND));
    }
}
