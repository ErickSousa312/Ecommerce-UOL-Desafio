package spring.domain.events.Login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoginEventListener_experimental {
    @EventListener
    public void onSimpleEvent(EventSuccessfulLogin_experimental event) {
        log.info("Login successful from {}", event.getMessage());
        System.out.println("Evento recebido com mensagem: " + event.getMessage());
    }
}
