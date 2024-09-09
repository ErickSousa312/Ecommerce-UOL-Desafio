package spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.domain.entities.product.model.Product;
import spring.domain.entities.sale.dto.CreateSaleDTO;
import spring.domain.entities.sale.model.QuantityProduct;
import spring.domain.entities.sale.model.Sale;
import spring.repositories.ProductRepository;
import spring.repositories.SaleRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@SpringBootTest
public  class MainTests {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SaleRepository saleRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateSale() {
        Product productTestttttt = new Product();

        Integer sumCountSale = 0;

        Map<Long,Integer> mapProduct = new LinkedHashMap<Long, Integer>();
        mapProduct.put(1L,10);
        mapProduct.put(2L,20);
        CreateSaleDTO saleDTO  = new CreateSaleDTO(LocalDateTime.now(), BigDecimal.valueOf(100.00),mapProduct);

        Sale sale = new Sale();
        sale.setDate(LocalDateTime.now());
        sale.setTotalAmount(BigDecimal.valueOf(00.00));
        Set<QuantityProduct> quantityProductsList = new LinkedHashSet<QuantityProduct>();

        for(Map.Entry<Long,Integer> entry : saleDTO.getProductQuantitiesAndIdProducts().entrySet()){
            QuantityProduct quantityProduct = new QuantityProduct();
            Long productId = entry.getKey();
            List<Product> listP = productRepository.findAll();
            int quantity = entry.getValue();
            Product product = productRepository.findById(productId)
                            .orElseThrow(() -> new IllegalArgumentException("Produto com ID " + productId + " não encontrado"));

            quantityProduct.setProduct(product);
            quantityProduct.setQuantity(quantity);
            product.reducerStock(quantity);
            sale.addAmount(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
            quantityProductsList.add(quantityProduct);
            quantityProduct.setSale(sale);
            productRepository.save(product);
        }
        sale.setProducts(quantityProductsList);
        System.out.println("iu");
        System.out.println(sale);
        Sale saleSaves = saleRepository.save(sale);
        System.out.println(saleSaves);
    }
}
