package spring;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import spring.domain.entities.product.enums.ProductStatus;
import spring.domain.entities.product.model.Product;
import spring.domain.entities.sale.model.Sale;
import spring.domain.repositories.ProductRepository;
import spring.domain.repositories.SaleRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@DataJpaTest
public  class MainTests {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testCreateSale() {
        //Criação do Produto
        Product product = new Product();
        product.setProductName("Test Product");
        product.setPrice(BigDecimal.valueOf(99.99));
        product.setStatus(ProductStatus.ACTIVE);
        product.setStock(50);
        product = productRepository.save(product);

        Sale sale = new Sale();
        sale.setDate(LocalDateTime.now());
        sale.setTotalAmount(BigDecimal.valueOf(199.99));
        sale.setProducts(Set.of(product));

        Sale savedSale = saleRepository.save(sale);
        Optional<Sale> findSale = saleRepository.findById(savedSale.getId());
        Set<Product> data = savedSale.getProducts();

        Optional<Product> Product = productRepository.findById(product.getId());

        assertThat(savedSale).isNotNull();
        assertThat(savedSale.getId()).isNotNull();
        assertThat(savedSale.getProducts()).isNotNull();
    }
}
