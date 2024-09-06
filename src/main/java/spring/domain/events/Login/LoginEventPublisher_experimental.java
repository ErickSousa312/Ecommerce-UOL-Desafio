package spring.domain.events.Login;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginEventPublisher_experimental {

    private final ApplicationEventPublisher eventPublisher;

    public void publish(String message) {
        EventSuccessfulLogin_experimental event = new EventSuccessfulLogin_experimental(this, message);
        eventPublisher.publishEvent(event);
    }
}
