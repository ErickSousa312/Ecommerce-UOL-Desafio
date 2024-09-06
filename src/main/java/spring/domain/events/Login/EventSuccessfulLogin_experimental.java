package spring.domain.events.Login;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class EventSuccessfulLogin_experimental extends ApplicationEvent {
    private final String message;
    public EventSuccessfulLogin_experimental(Object source, String message) {
        super(source);
        this.message = message;
    }

    public EventSuccessfulLogin_experimental(Object source, Clock clock, String message) {
        super(source, clock);
        this.message = message;
    }
}
