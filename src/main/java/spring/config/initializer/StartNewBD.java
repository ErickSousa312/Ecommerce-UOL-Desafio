package spring.config.initializer;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import spring.domain.entities.user.model.Authority;
import spring.domain.entities.user.model.Customer;
import spring.domain.repositories.CustomerRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class StartNewBD {

    private final CustomerRepository customerRepository;

    @EventListener(ApplicationReadyEvent .class)
    public void initializerDataBase(){
        createDefaultUser("erick","teste@gmail.com","123","admin");
        createDefaultUser("erick2","teste2@gmail.com","1232","admin");
    }

    public void createDefaultUser(String name, String email, String password, String role) {
        try {
            List<Customer> customers = customerRepository.findAll();
            if(customers.isEmpty()){

                Authority authority1 = new Authority();
                authority1.setName("ROLE_USER");

                Authority authority2 = new Authority();
                authority2.setName("ROLE_ADMIN");

                Set<Authority> authorities = new HashSet<>();
                authorities.add(authority1);
                authorities.add(authority2);

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
