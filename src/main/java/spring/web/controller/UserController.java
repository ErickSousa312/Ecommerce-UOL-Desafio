package spring.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.constants.ApplicationConstants;
import spring.domain.entities.user.model.Authority;
import spring.domain.entities.user.model.Customer;
import spring.domain.entities.user.dto.LoginRequestDTO;
import spring.domain.entities.user.dto.LoginResponseDTO;
import spring.repositories.CustomerRepository;
import spring.services.JWTService;

@RestController()
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final Environment env;
    private final JWTService jwtService;

    @PostMapping("/register")
    ResponseEntity<?> RegisterUser(@RequestBody Customer customer) {
        try {

            String encodedPassword = passwordEncoder.encode(customer.getPws());

            if(customerRepository.existsByEmail(customer.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
            }

            for (Authority authority : customer.getAuthority()) {
                authority.setCustomer(customer);
            }
            customer.setPws(encodedPassword);
            Customer newUser = customerRepository.save(customer);
            if (newUser.getId() > 0) {
                return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
            }
            return ResponseEntity.status(400).body("user not register");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }
    @PostMapping("/apiLogin")
    public ResponseEntity<LoginResponseDTO> Login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        String jwt = "";
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getUserName(), loginRequest.getPassword());
        Authentication authenticationResponse = authenticationManager.authenticate(authentication);
        if(authenticationResponse.isAuthenticated()) {
            jwt = jwtService.generateJWT(authenticationResponse);
        }
        return ResponseEntity.status(HttpStatus.OK).header(ApplicationConstants.JWT_HEADER,jwt)
                .body(new LoginResponseDTO(HttpStatus.OK.getReasonPhrase(),jwt));
    }


}
