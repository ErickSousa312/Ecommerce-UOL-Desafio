package spring.domain.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.domain.entities.product.model.Product;
import spring.domain.entities.sale.dto.CreateSaleDTO;
import spring.domain.entities.sale.model.QuantityProduct;
import spring.domain.entities.sale.model.Sale;
import spring.domain.repositories.ProductRepository;
import spring.domain.repositories.SaleRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;


    @Transactional
    public Sale save(CreateSaleDTO saleDTO){
        Sale saleCreated = findAndInsertProductAtNewSale(saleDTO);
        return saleRepository.save(saleCreated);
    }

    private Sale findAndInsertProductAtNewSale(CreateSaleDTO saleDTO ){
        Sale sale = new Sale();
        sale.setDate(LocalDateTime.now());
        sale.setTotalAmount(BigDecimal.valueOf(00.00));
        Set<QuantityProduct> quantityProductsList = new LinkedHashSet<QuantityProduct>();

        for(Map.Entry<Long,Integer> entry : saleDTO.getProductQuantitiesAndIdProducts().entrySet()){
            QuantityProduct quantityProduct = new QuantityProduct();
            Long productId = entry.getKey();
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
        return sale;
    }

}
