package spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.model.Costumer;
import spring.repository.CustomerRepository;

import java.beans.Encoder;

@RestController()
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    ResponseEntity<String> RegisterUser(@RequestBody Costumer costumer) {
        try {
            String encodedPassword = passwordEncoder.encode(costumer.getPws());
            costumer.setPws(encodedPassword);
            Costumer newUser = customerRepository.save(costumer);
            if (newUser.getId() > 0) {
                return ResponseEntity.status(HttpStatus.CREATED).body(newUser.toString());
            }
            return ResponseEntity.status(400).body("user not register");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }
}
