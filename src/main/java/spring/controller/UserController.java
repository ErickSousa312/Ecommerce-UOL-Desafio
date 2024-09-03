package spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.model.Customer;
import spring.repository.CustomerRepository;

@RestController()
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    ResponseEntity<String> RegisterUser(@RequestBody Customer customer) {
        try {
            String encodedPassword = passwordEncoder.encode(customer.getPws());
            customer.setPws(encodedPassword);
            Customer newUser = customerRepository.save(customer);
            if (newUser.getId() > 0) {
                return ResponseEntity.status(HttpStatus.CREATED).body(newUser.toString());
            }
            return ResponseEntity.status(400).body("user not register");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }
}
