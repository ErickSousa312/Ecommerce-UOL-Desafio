package spring;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.eventListener.LoginEventPublisher;

@RestController
@RequiredArgsConstructor
public class WelcomeController {

    private final LoginEventPublisher loginEventPublisher;

    @GetMapping("/wellcome")
    public String welcome() {
        return "Hello World";
    }
    @GetMapping("/info")
    public String userInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String authorities = authentication.getAuthorities().toString();
        return "Usuário: " + username + ", Permissões: " + authorities;
    }
    @GetMapping("/event")
    public String event() {
        loginEventPublisher.publish("usuario: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return "Evento publicado com sucesso!";
    }
}
