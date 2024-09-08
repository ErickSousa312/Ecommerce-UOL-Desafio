package spring.domain.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.domain.entities.product.model.Product;
import spring.domain.entities.sale.dto.CreateSaleDTO;
import spring.domain.entities.sale.model.QuantityProduct;
import spring.domain.entities.sale.model.Sale;
import spring.domain.entities.user.model.Customer;
import spring.domain.repositories.CustomerRepository;
import spring.domain.repositories.ProductRepository;
import spring.domain.repositories.QuantityProductRepository;
import spring.domain.repositories.SaleRepository;
import spring.web.execption.EntityNotFound;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final QuantityProductRepository quantityProductRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Sale save(CreateSaleDTO saleDTO) {
        Sale saleCreated = findAndInsertProductAtNewSale(saleDTO);
        return saleRepository.save(saleCreated);
    }

    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    public Sale findById(Long id) {
        return saleRepository.findById(id).orElseThrow(() -> new EntityNotFound("Sale", HttpStatus.NOT_FOUND));
    }

    @Transactional
    public Sale updateById(Long id, CreateSaleDTO saleDTO) {
        Sale saleUpdated = updateProductAtExistSale(saleDTO, id);
        return saleRepository.save(saleUpdated);
    }


    private Sale findAndInsertProductAtNewSale(CreateSaleDTO saleDTO) {
        Sale sale = new Sale();
        sale.setDate(LocalDateTime.now());
        sale.setTotalAmount(BigDecimal.valueOf(00.00));
        Set<QuantityProduct> quantityProductsList = new LinkedHashSet<>();

        Customer client = customerRepository.findById(saleDTO.getClientId()).orElseThrow(() -> new EntityNotFound("User ID: " + saleDTO.getClientId(), HttpStatus.NOT_FOUND));
        sale.setCostumer(client);

        for (Map.Entry<Long, Integer> entry : saleDTO.getProductQuantitiesAndIdProducts().entrySet()) {
            QuantityProduct quantityProduct = new QuantityProduct();
            Long productId = entry.getKey();
            int quantity = entry.getValue();
            Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFound("Product ID: " + productId, HttpStatus.NOT_FOUND));

            quantityProduct.setProduct(product);
            quantityProduct.setQuantity(quantity);
            product.reducerStock(quantity);
            sale.addAmount(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
            quantityProductsList.add(quantityProduct);
            quantityProduct.setSale(sale);
            productRepository.save(product);
        }

        sale.setProducts(quantityProductsList);
        return sale;
    }


    private Sale updateProductAtExistSale(CreateSaleDTO saleDTO, Long id) {
        Sale sale = findById(id);
        sale.setDate(saleDTO.getDate());
        sale.setTotalAmount(BigDecimal.valueOf(00.00));
        Set<QuantityProduct> quantityProductsList = new LinkedHashSet<>();

        deleteProductsSales(sale);

        System.out.println("t");

        for (Map.Entry<Long, Integer> entry : saleDTO.getProductQuantitiesAndIdProducts().entrySet()) {
            QuantityProduct quantityProduct = new QuantityProduct();
            Long productId = entry.getKey();
            int quantity = entry.getValue();
            Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFound("Product ID: " + productId, HttpStatus.NOT_FOUND));

            quantityProduct.setProduct(product);
            quantityProduct.setQuantity(quantity);
            product.reducerStock(quantity);
            sale.addAmount(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
            quantityProductsList.add(quantityProduct);
            quantityProduct.setSale(sale);
            productRepository.save(product);
        }

        sale.setProducts(quantityProductsList);
        return sale;
    }

    public void deleteProductsSales(Sale sale){
        List<Integer> quantityToDelete = new ArrayList<>();
        for(QuantityProduct Qproducts : sale.getProducts()){
            Product product = productRepository.findById(Qproducts.getProduct().getId()).orElseThrow(
                    () -> new EntityNotFound("Product ID: " + Qproducts.getProduct().getId(), HttpStatus.NOT_FOUND));
            product.addStock(Qproducts.getQuantity());
            quantityToDelete.add(Qproducts.getId());
            productRepository.save(product);
        }
        sale.getProducts().clear();
        for(Integer quantity : quantityToDelete){
            quantityProductRepository.deleteById(quantity);
        }
        entityManager.flush();
    }

}
