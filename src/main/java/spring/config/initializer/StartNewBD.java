package spring.config.initializer;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import spring.domain.entities.product.enums.ProductStatus;
import spring.domain.entities.product.model.Product;
import spring.domain.entities.user.model.Authority;
import spring.domain.entities.user.model.Customer;
import spring.repositories.CustomerRepository;
import spring.repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class StartNewBD {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @EventListener(ApplicationReadyEvent .class)
    public void initializerDataBase(){
        createDefaultUser("erick","teste@gmail.com","123","admin");
        createDefaultUser("erick2","teste1@gmail.com","1232","user");
        createDefaultProduct();
    }

    public void createDefaultProduct(){
        Product product1 = new Product();
        product1.setId(1L);
        product1.setProductName("Produto 1");
        product1.setPrice(BigDecimal.valueOf(50.00));
        product1.setStock(100);
        product1.setStatus(ProductStatus.ACTIVE);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setProductName("Produto 2");
        product2.setPrice(BigDecimal.valueOf(75.00));
        product2.setStock(200);
        product2.setStatus(ProductStatus.ACTIVE);
        productRepository.save(product2);
    }

    public void createDefaultUser(String name, String email, String password, String role) {
        try {
            List<Customer> customers = customerRepository.findAll();
            if(true){

                Authority authority1 = new Authority();
                authority1.setName("ROLE_USER");

                Set<Authority> authorities = new HashSet<>();
                authorities.add(authority1);

                Authority authority2 = new Authority();
                if(customers.isEmpty()){
                    authority2.setName("ROLE_ADMIN");
                    authorities.add(authority2);
                }

                Customer user = new Customer();
                user.setName(name);
                user.setEmail(email);
                user.setPws(password);
                user.setRole(role);
                user.setAuthority(authorities);

                for(Authority authority : user.getAuthority()){
                    authority.setCustomer(user);
                }

                customerRepository.save(user);
            }
        } catch (RuntimeException e) {
            return;
        }
    }
}
