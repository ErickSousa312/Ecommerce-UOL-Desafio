package spring;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.config.security.userDetails.UserDetailsImpl;
import spring.domain.events.Login.LoginEventPublisher_experimental;

@RestController
@RequiredArgsConstructor
public class WelcomeController {

    private final LoginEventPublisher_experimental loginEventPublisherExperimental;

    @PostMapping("/wellcome")
    public String welcome(Authentication authentication) {
        System.out.println(authentication);
        return "Hello World";
    }
    @GetMapping("/info")
    public String userInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String username = authentication.getName();
        String authorities = authentication.getAuthorities().toString();
        return "Usuário: " + username + ", Permissões: " + authorities + "  Dados adicionais:" + userDetails.getAdditionalParameter();
    }
    @GetMapping("/event")
    public String event() {
        loginEventPublisherExperimental.publish("usuario: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return "Evento publicado com sucesso!";
    }
}
