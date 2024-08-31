package spring.eventListener;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public void publish(String message) {
        EventSuccessfulLogin event = new EventSuccessfulLogin(this, message);
        eventPublisher.publishEvent(event);
    }
}
