package spring.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.constants.ApplicationConstants;
import spring.domain.entities.user.dto.ResetPasswordDTO;
import spring.domain.entities.user.model.Authority;
import spring.domain.entities.user.model.Customer;
import spring.domain.entities.user.dto.LoginRequestDTO;
import spring.domain.entities.user.dto.LoginResponseDTO;
import spring.repositories.CustomerRepository;
import spring.services.EmailService;
import spring.services.JWTService;
import spring.web.exceptions.BadCredentialsException;

import java.util.Random;

@RestController()
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final Environment env;
    private final JWTService jwtService;
    private final EmailService emailService;

    @PreAuthorize("hasRole('ADMIN')")
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

    @GetMapping("/forgot_password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        try{
            if(email == null || email.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is required");
            }
            if(!customerRepository.existsByEmail(email)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email does not exist");
            }
            emailService.ResetPasswordByEmail(email);
            return ResponseEntity.status(HttpStatus.OK).body("email link para reset de senha enviado para email");
        }catch (MailException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/call_back_password")
    public ResponseEntity<?> callBackPassword(@RequestParam String token) {
        try{
            String emailToken = jwtService.getEmailByToken(token);
            if(emailToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email token invalid");
            }
            Random random = new Random();
            int randomNumber = 100000 + random.nextInt(900000);
            Customer customer = customerRepository.findByEmail(emailToken).orElseThrow(()->new RuntimeException("Email not found"));
            String newPassword = passwordEncoder.encode(randomNumber + "");
            customer.setPws(newPassword);
            customerRepository.save(customer);
            emailService.send(emailToken,"Reset de senha", "Sua nova senha é: "
                    + randomNumber + ", por favor a troque-a o mais breve possivel");
            return ResponseEntity.status(HttpStatus.OK).body("senha resetada com sucesso, sua nova senha foi enviada por email");
        }catch (MailException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/reset_password")
    public ResponseEntity<?> ResetPassword(Authentication authentication, @RequestBody ResetPasswordDTO resetPasswordDTO) {
        Customer user = customerRepository.findByEmail(authentication.getPrincipal().toString()).get();
        if(!passwordEncoder.matches(resetPasswordDTO.getOldPassword(), user.getPws())) {
            throw new BadCredentialsException("Old password no match", HttpStatus.BAD_REQUEST);
        }
        user.setPws(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
        customerRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
