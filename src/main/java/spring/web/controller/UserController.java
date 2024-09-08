package spring.web.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.constants.ApplicationConstants;
import spring.domain.entities.user.model.Authority;
import spring.domain.entities.user.model.Customer;
import spring.domain.entities.user.dto.LoginRequestDTO;
import spring.domain.entities.user.dto.LoginResponseDTO;
import spring.domain.repositories.CustomerRepository;
import spring.domain.services.JWTService;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

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
    ResponseEntity<String> RegisterUser(@RequestBody Customer customer) {
        try {
            String encodedPassword = passwordEncoder.encode(customer.getPws());

            for (Authority authority : customer.getAuthority()) {
                authority.setCustomer(customer);
            }
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
    @PostMapping("/apiLogin")
    public ResponseEntity<LoginResponseDTO> Login(@RequestBody LoginRequestDTO loginRequest) {
        String jwt = "";
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.userName(), loginRequest.password());
        Authentication authenticationResponse = authenticationManager.authenticate(authentication);
        if(authenticationResponse.isAuthenticated()) {
            jwt = jwtService.generateJWT(authenticationResponse);
        }
        return ResponseEntity.status(HttpStatus.OK).header(ApplicationConstants.JWT_HEADER,jwt)
                .body(new LoginResponseDTO(HttpStatus.OK.getReasonPhrase(),jwt));
    }
}
