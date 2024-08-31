package spring.eventListener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class LoginEventListener {
    @EventListener
    public void onSimpleEvent(EventSuccessfulLogin event) {
        System.out.println("Evento recebido com mensagem: " + event.getMessage());
    }
}
