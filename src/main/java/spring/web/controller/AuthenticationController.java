package spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.services.AuthenticationService;

@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("teste")
    public String test (){
        return "ou";
    }

    @PostMapping("authenticate")
    public String authenticate(Authentication authentication){
        System.out.println("entrou");
        return authenticationService.authenticate(authentication);
    }

}
